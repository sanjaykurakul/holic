package com.udyogam.service;



import org.springframework.stereotype.Service;

import com.udyogam.repository.ApplicationRepository;
import com.udyogam.repository.JobRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    private final JobRepository jobRepo;
    private final ApplicationRepository appRepo;

    public AnalyticsService(JobRepository jobRepo, ApplicationRepository appRepo) {
        this.jobRepo = jobRepo;
        this.appRepo = appRepo;
    }

    // Employer Dashboard
    public Map<String, Long> getEmployerStats(Long employerId) {

        Map<String, Long> data = new HashMap<>();

        data.put("totalJobs", jobRepo.countByEmployerId(employerId));
        data.put("totalApplicants", appRepo.countByJobEmployerId(employerId));
        data.put("shortlisted",
                appRepo.countByJobEmployerIdAndStatus(employerId, "SHORTLISTED"));

        return data;
    }

    // Student Dashboard
    public Map<String, Long> getStudentStats(Long userId) {

        Map<String, Long> data = new HashMap<>();

        data.put("totalApplications", appRepo.countByUserId(userId));
        data.put("applied", appRepo.countByUserIdAndStatus(userId, "APPLIED"));
        data.put("shortlisted", appRepo.countByUserIdAndStatus(userId, "SHORTLISTED"));
        data.put("rejected", appRepo.countByUserIdAndStatus(userId, "REJECTED"));

        return data;
    }
}