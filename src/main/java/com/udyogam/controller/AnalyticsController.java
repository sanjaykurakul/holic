package com.udyogam.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.udyogam.service.AnalyticsService;

@Controller
@RequestMapping("/dashboard")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    // Employer Dashboard View
    @GetMapping("/employer/{id}")
    public String employerDashboard(@PathVariable Long id, Model model) {

        model.addAttribute("stats", service.getEmployerStats(id));
        return "employer-dashboard";
    }

    // Student Dashboard View
    @GetMapping("/student/{id}")
    public String studentDashboard(@PathVariable Long id, Model model) {

        model.addAttribute("stats", service.getStudentStats(id));
        return "student-dashboard";
    }
}