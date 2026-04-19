package com.udyogam.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udyogam.entity.Job;
import com.udyogam.repository.JobRepository;
import com.udyogam.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService service;
    private final JobRepository repo;

    public JobController(JobService service, JobRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @PostMapping("/create/{employerId}")
    public ResponseEntity<?> createJob(@RequestBody Job job,
                                       @PathVariable Long employerId) {
        try {
            return ResponseEntity.ok(service.createJob(job, employerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Job creation failed");
        }
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Job>> getByLocation(@PathVariable String location) {
        return ResponseEntity.ok(repo.findByLocation(location));
    }

    @GetMapping("/skills/{skills}")
    public ResponseEntity<List<Job>> getBySkills(@PathVariable String skills) {
        return ResponseEntity.ok(repo.findBySkillsContaining(skills));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok("Job deleted");
    }
}