package com.udyogam.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.Application;
import com.udyogam.repository.ApplicationRepository;
import com.udyogam.service.ApplicationService;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService service;
    private final ApplicationRepository repo;

    public ApplicationController(ApplicationService service,
                                 ApplicationRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestParam Long userId,
                                   @RequestParam Long jobId) {
        try {
            return ResponseEntity.ok(service.applyForJob(userId, jobId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Application failed");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getUserApplications(@PathVariable Long userId) {
        return ResponseEntity.ok(repo.findByUserId(userId));
    }
}