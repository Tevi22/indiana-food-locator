package com.indiana.foodlocator.controller;

import com.indiana.foodlocator.entity.User;
import com.indiana.foodlocator.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute User user,
            Model model) {

        try {

            userService.registerUser(user);

            return "redirect:/login?registered";

        } catch (IllegalArgumentException exception) {

            model.addAttribute(
                    "registrationError",
                    exception.getMessage());

            return "register";
        }
    }
}
