package com.udyogam.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @NotNull
    @Column(length = 1000)
    private String description;

    @NotNull
    private String skills;

    private Double salary;

    @NotNull
    private String location;

    // Many jobs belong to one employer
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User employer;

    // One job can have many applications
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Application> applications;
}