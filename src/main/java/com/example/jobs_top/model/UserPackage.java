package com.example.jobs_top.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "_recruiter_package")
public class UserPackage extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    private RecruiterProfile recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private Package pack;
    private LocalDate startDate;
    private LocalDate endDate;
}
