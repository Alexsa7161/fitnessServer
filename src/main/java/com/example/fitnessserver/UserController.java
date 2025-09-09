package com.example.fitnessserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class UserController {
    @GetMapping("/user")
    public String userPage(@SessionAttribute(name = "user", required = false) User user, Model model) {
        if (user == null) {
            model.addAttribute("userId", "Неизвестный пользователь");
            return "redirect:/login"; // или просто return "user", если хочешь показать страницу
        }

        model.addAttribute("userId", user.getUserId());
        return "user";
    }
}
