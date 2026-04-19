package com.udyogam.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String status; // APPLIED / SHORTLISTED / REJECTED

    private LocalDate appliedDate;

    // Many applications belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many applications belong to one job
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}