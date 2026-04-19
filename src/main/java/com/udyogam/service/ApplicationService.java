package com.udyogam.service;


import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.udyogam.entity.Application;
import com.udyogam.entity.Job;
import com.udyogam.entity.User;
import com.udyogam.repository.ApplicationRepository;
import com.udyogam.repository.JobRepository;
import com.udyogam.repository.UserRepository;

@Service
public class ApplicationService {

    private final ApplicationRepository appRepo;
    private final UserRepository userRepo;
    private final JobRepository jobRepo;
    //private final EmailService emailService;

    public ApplicationService(ApplicationRepository appRepo,
                              UserRepository userRepo,
                              JobRepository jobRepo
                              ) {
        this.appRepo = appRepo;
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        //this.emailService = emailService;
    }

    public Application applyForJob(Long userId, Long jobId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (appRepo.findByUserId(userId)
                .stream()
                .anyMatch(a -> a.getJob().getId().equals(jobId))) {
            throw new RuntimeException("Already applied for this job");
        }

        Application app = new Application();
        app.setUser(user);
        app.setJob(job);
        app.setStatus("APPLIED");
        app.setAppliedDate(LocalDate.now());

        Application saved = appRepo.save(app);

//        // 📧 Send email to user
//        //emailService.sendMail(
//                user.getEmail(),
//                "Job Application Submitted",
//                "You have successfully applied for: " + job.getTitle()
//        );//

        return saved;
    }

    public Application shortlistCandidate(Long applicationId) {

        Application app = appRepo.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus("SHORTLISTED");
        Application updated = appRepo.save(app);

        // 📧 Notify user
//        emailService.sendMail(
//                app.getUser().getEmail(),
//                "Application Update",
//                "Congratulations! You have been shortlisted for: " +
//                        app.getJob().getTitle()
//        );

        return updated;
    }

}