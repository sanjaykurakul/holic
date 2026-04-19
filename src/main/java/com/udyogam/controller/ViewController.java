package com.udyogam.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.User;
import com.udyogam.entity.Job;
import com.udyogam.repository.JobRepository;
import com.udyogam.repository.UserRepository;
import com.udyogam.repository.ApplicationRepository;
import com.udyogam.repository.NotificationRepository;
import com.udyogam.service.ApplicationService;
import com.udyogam.service.JobService;

@Controller
public class ViewController {

    private final JobRepository jobRepo;
    private final UserRepository userRepo;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepo;
    private final NotificationRepository notificationRepo;

    public ViewController(JobRepository jobRepo, UserRepository userRepo, JobService jobService, ApplicationService applicationService, ApplicationRepository applicationRepo, NotificationRepository notificationRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
        this.jobService = jobService;
        this.applicationService = applicationService;
        this.applicationRepo = applicationRepo;
        this.notificationRepo = notificationRepo;
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

    @GetMapping("/view-applications")
    public String viewApplications(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        model.addAttribute("applications", applicationRepo.findAll());
        return "employer-applications";
    }

    @GetMapping("/manage-jobs")
    public String manageJobs(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        User employer = userRepo.findByEmail(principal.getName()).orElseThrow();
        // Here we just fetch all jobs for now, or preferably filter by employer
        // But Job doesn't currently easily filter by employer in repository without a method
        // Let's assume jobRepo.findAll() or we add a method
        model.addAttribute("jobs", jobRepo.findAll().stream().filter(j -> j.getEmployer() != null && j.getEmployer().getId().equals(employer.getId())).toList());
        return "manage-jobs";
    }

    @GetMapping("/edit-job/{id}")
    public String editJobPage(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        Job job = jobRepo.findById(id).orElseThrow();
        model.addAttribute("job", job);
        return "edit-job";
    }

    @PostMapping("/update-job/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute Job jobDetails, Principal principal) {
        if (principal == null) return "redirect:/login";
        Job job = jobRepo.findById(id).orElseThrow();
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setSkills(jobDetails.getSkills());
        job.setSalary(jobDetails.getSalary());
        job.setLocation(jobDetails.getLocation());
        jobRepo.save(job);
        return "redirect:/manage-jobs";
    }

    @PostMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable Long id, Principal principal) {
        if (principal == null) return "redirect:/login";
        jobRepo.deleteById(id);
        return "redirect:/manage-jobs";
    }
}