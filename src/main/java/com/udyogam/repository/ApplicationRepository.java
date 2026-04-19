package com.udyogam.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udyogam.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    long countByJobEmployerId(Long employerId);

    long countByJobEmployerIdAndStatus(Long employerId, String status);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, String status);
}