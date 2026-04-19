package com.udyogam.controller;





import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.User;
import com.udyogam.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = service.register(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed");
        }
    }

    // Login handled by Spring Security (/login)
}