package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.User;
import com.nhahang.nhahang_web.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<User> userOpt = userService.login(username, password);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole().toString());
            return "redirect:/index";
        }
        model.addAttribute("error", "Sai tài khoản hoặc mật khẩu");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String hoTen,
                           @RequestParam String email,
                           Model model) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setHoTen(hoTen);
        user.setEmail(email);
        user.setRole(User.Role.NHANVIEN); // mặc định

        try {
            userService.save(user);  // Gọi API /register
            model.addAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
            return "login";

        } catch (org.springframework.web.client.HttpClientErrorException.Conflict e) {
            String errorMessage = "Tài khoản đã tồn tại.";
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Map<String, String> errorBody = mapper.readValue(e.getResponseBodyAsString(), Map.class);
                errorMessage = errorBody.getOrDefault("message", errorMessage);
            } catch (Exception ignored) {
            }
            model.addAttribute("error", errorMessage);
            return "register";

        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hệ thống khi đăng ký. Vui lòng thử lại sau.");
            return "register";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            model.addAttribute("success", "Email hợp lệ. Vui lòng nhập mật khẩu mới.");
            model.addAttribute("email", email);
            return "reset-password";
        }
        model.addAttribute("error", "Email không tồn tại!");
        return "forgot-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email,
                                @RequestParam String newPassword,
                                Model model) {
        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            try {
                userService.resetPassword(email, newPassword); 
                model.addAttribute("success", "Đặt lại mật khẩu thành công. Vui lòng đăng nhập.");
                return "login";
            } catch (Exception e) {
                model.addAttribute("error", "Lỗi khi cập nhật mật khẩu.");
                return "reset-password";
            }
        }
        model.addAttribute("error", "Không tìm thấy tài khoản với email đã nhập.");
        return "reset-password";
    }
}
