package com.udyogam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.udyogam.entity.User;
import com.udyogam.service.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        try {
            service.register(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed");
            return "register";
        }
    }

    // Login handled by Spring Security (/login)
}