package com.example.customerfeedback.controller;

import com.example.customerfeedback.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Home page
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Registration page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Process registration
    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        try {

            userService.register(name, email, password);

            return "redirect:/login?registered";

        } catch (IllegalArgumentException ex) {

            model.addAttribute("error", ex.getMessage());

            return "register";
        }
    }
}