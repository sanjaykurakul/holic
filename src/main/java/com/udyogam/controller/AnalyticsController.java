package com.udyogam.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.User;
import com.udyogam.repository.UserRepository;
import com.udyogam.repository.NotificationRepository;
import com.udyogam.service.AnalyticsService;

@Controller
public class AnalyticsController {

    private final AnalyticsService service;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public AnalyticsController(AnalyticsService service, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    // Employer Dashboard View
    @GetMapping("/employer-dashboard")
    public String employerDashboard(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("stats", service.getEmployerStats(user.getId()));
        return "employer-dashboard";
    }

    // Student Dashboard View
    @GetMapping("/student-dashboard")
    public String studentDashboard(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("stats", service.getStudentStats(user.getId()));
        model.addAttribute("resumePath", user.getResumePath());
        model.addAttribute("notifications", notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId()));
        return "student-dashboard";
    }
}