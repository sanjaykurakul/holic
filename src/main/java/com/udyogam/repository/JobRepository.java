package com.udyogam.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udyogam.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByLocation(String location);

    List<Job> findBySkillsContaining(String skills);
    long countByEmployerId(Long employerId);
}