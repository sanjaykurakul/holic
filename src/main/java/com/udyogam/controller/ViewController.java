package com.udyogam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.udyogam.repository.JobRepository;

@Controller
public class ViewController {

    private final JobRepository jobRepo;

    public ViewController(JobRepository jobRepo) {
        this.jobRepo = jobRepo;
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
    public String home() {
        return "job-list";
    }

    @GetMapping("/jobs-page")
    public String jobs(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        return "job-list";
    }

    @GetMapping("/apply/{id}")
    public String applyPage(@PathVariable Long id, Model model) {
        model.addAttribute("jobId", id);
        model.addAttribute("userId", 1); // TEMP (later replace with logged-in user)
        return "apply-job";
    }

    @GetMapping("/post-job")
    public String postJob(Model model) {
        model.addAttribute("employerId", 1); // TEMP
        return "post-job";
    }

}