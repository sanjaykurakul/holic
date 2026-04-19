package com.udyogam.service;



import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udyogam.entity.User;
import com.udyogam.repository.UserRepository;
import com.udyogam.config.FileStorageConfig;

@Service
public class ResumeService {

    private final UserRepository userRepo;
    private final FileStorageConfig config;

    public ResumeService(UserRepository userRepo, FileStorageConfig config) {
        this.userRepo = userRepo;
        this.config = config;
    }

    public String uploadResume(Long userId, MultipartFile file) throws IOException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String uploadDir = config.getUploadDir();

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = userId + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        file.transferTo(new File(filePath));

        user.setResumePath(filePath);
        userRepo.save(user);

        return filePath;
    }
}