package com.udyogam.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.User;
import com.udyogam.repository.JobRepository;
import com.udyogam.repository.UserRepository;
import com.udyogam.service.ApplicationService;
import com.udyogam.service.JobService;

@Controller
public class ViewController {

    private final JobRepository jobRepo;
    private final UserRepository userRepo;
    private final JobService jobService;
    private final ApplicationService applicationService;

    public ViewController(JobRepository jobRepo, UserRepository userRepo, JobService jobService, ApplicationService applicationService) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.jobService = jobService;
        this.applicationService = applicationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/home")
    public String home(Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        if ("EMPLOYER".equalsIgnoreCase(user.getRole())) {
            return "redirect:/employer-dashboard";
        }
        return "redirect:/student-dashboard";
    }

    @GetMapping("/jobs-page")
    public String jobs(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        return "job-list";
    }

    @GetMapping("/apply/{id}")
    public String applyPage(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        model.addAttribute("jobId", id);
        model.addAttribute("userId", user.getId());
        return "apply-job";
    }

    @GetMapping("/post-job")
    public String postJob(Model model) {
        return "post-job";
    }

    @PostMapping("/post-job")
    public String createJob(@ModelAttribute com.udyogam.entity.Job job, Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        jobService.createJob(job, user.getId());
        return "redirect:/employer-dashboard";
    }

    @PostMapping("/apply-job")
    public String submitApplication(@RequestParam Long jobId, Principal principal) {
        if (principal == null) return "redirect:/login";
        User user = userRepo.findByEmail(principal.getName()).orElseThrow();
        try {
            applicationService.applyForJob(user.getId(), jobId);
        } catch (Exception e) {
            return "redirect:/jobs-page?error";
        }
        return "redirect:/student-dashboard";
    }
}