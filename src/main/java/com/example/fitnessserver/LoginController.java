package com.example.fitnessserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("user_id") String userId, HttpSession session, Model model) {
        if (userId == null || userId.trim().isEmpty()) {
            model.addAttribute("error", "Введите ID пользователя.");
            return "login";
        }

        User user = new User(userId);
        session.setAttribute("user", user);
        return "redirect:/user";
    }
}