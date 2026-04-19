package com.udyogam.service;



import org.springframework.stereotype.Service;

import com.udyogam.entity.Job;
import com.udyogam.entity.User;
import com.udyogam.repository.JobRepository;
import com.udyogam.repository.UserRepository;

@Service
public class JobService {

    private final JobRepository jobRepo;
    private final UserRepository userRepo;

    public JobService(JobRepository jobRepo, UserRepository userRepo) {
        this.jobRepo = jobRepo;
        this.userRepo = userRepo;
    }

    public Job createJob(Job job, Long employerId) {
        User employer = userRepo.findById(employerId)
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        job.setEmployer(employer);
        return jobRepo.save(job);
    }
}