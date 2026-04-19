package com.udyogam.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.Application;
import com.udyogam.repository.ApplicationRepository;
import com.udyogam.service.ApplicationService;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    private final ApplicationRepository repo;
    private final ApplicationService service;

    public EmployerController(ApplicationRepository repo,
                              ApplicationService service) {
        this.repo = repo;
        this.service = service;
    }

    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplicants() {
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping("/shortlist/{id}")
    public ResponseEntity<?> shortlist(@PathVariable Long id) {
        return ResponseEntity.ok(service.shortlistCandidate(id));
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        Application app = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus("REJECTED");
        repo.save(app);

        return ResponseEntity.ok("Candidate rejected");
    }
}