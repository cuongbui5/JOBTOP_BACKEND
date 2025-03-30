package com.example.jobs_top.model;

import jakarta.persistence.*;

@Entity
@Table(name = "_conversation")
public class Conversation extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id",referencedColumnName = "id")
    private RecruiterProfile recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_seeker_id",referencedColumnName = "id")
    private User jobSeeker;
}
