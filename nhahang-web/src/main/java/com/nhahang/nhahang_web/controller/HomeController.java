package com.nhahang.nhahang_web.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || role == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "index"; // => /templates/index.html
    }
}
