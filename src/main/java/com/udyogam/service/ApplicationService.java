package com.udyogam.service;


import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.udyogam.entity.Application;
import com.udyogam.entity.Job;
import com.udyogam.entity.User;
import com.udyogam.repository.ApplicationRepository;
import com.udyogam.repository.JobRepository;
import com.udyogam.repository.UserRepository;
import com.udyogam.repository.NotificationRepository;
import com.udyogam.entity.Notification;
import java.time.LocalDateTime;

@Service
public class ApplicationService {

    private final ApplicationRepository appRepo;
    private final UserRepository userRepo;
    private final JobRepository jobRepo;
    private final NotificationRepository notificationRepo;

    public ApplicationService(ApplicationRepository appRepo,
                              UserRepository userRepo,
                              JobRepository jobRepo,
                              NotificationRepository notificationRepo
                              ) {
        this.appRepo = appRepo;
        this.userRepo = userRepo;
        this.jobRepo = jobRepo;
        this.notificationRepo = notificationRepo;
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

        // Notify user
        Notification notification = new Notification();
        notification.setUser(app.getUser());
        notification.setMessage("Congratulations! You have been shortlisted for: " + app.getJob().getTitle());
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepo.save(notification);

        return updated;
    }

}