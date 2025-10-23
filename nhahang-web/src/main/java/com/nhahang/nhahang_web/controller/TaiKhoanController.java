package com.nhahang.nhahang_web.controller;

import com.nhahang.nhahang_web.model.User;
import com.nhahang.nhahang_web.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/taikhoan")
public class TaiKhoanController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String listAccounts(Model model) {
        model.addAttribute("listTaiKhoan", userService.getAll());
        return "taikhoan";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("listTaiKhoan", userService.search(keyword));
        return "taikhoan";
    }

    @GetMapping("/changerole/{id}")
    public String changeRole(@PathVariable int id) {
        userService.changeRole(id);
        return "redirect:/taikhoan";
    }

    // Lấy thông tin user hiện tại
    @ResponseBody
    @GetMapping("/current")
    public ResponseEntity<User> getCurrent(@RequestParam String username) {
        return userService.getCurrentUser(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Đổi mật khẩu
    @ResponseBody
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String username, @RequestBody Map<String, String> body) {
        return userService.changePassword(
                username,
                body.get("currentPassword"),
                body.get("newPassword"),
                body.get("confirmPassword")
        );
    }

    @GetMapping("/detail")
    public String accountDetail(@RequestParam(required = false) String username, Model model) {
        if (username == null) {
            // Nếu muốn, lấy username từ session, hoặc redirect về trang chủ
            return "redirect:/"; 
        }
        model.addAttribute("username", username);
        return "account";
    }


}
