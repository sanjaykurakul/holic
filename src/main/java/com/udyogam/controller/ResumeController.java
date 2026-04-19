package com.udyogam.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.udyogam.service.ResumeService;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService service;

    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam Long userId,
                                    @RequestParam MultipartFile file) {
        try {
            String path = service.uploadResume(userId, file);
            return ResponseEntity.ok("Uploaded: " + path);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed");
        }
    }
}