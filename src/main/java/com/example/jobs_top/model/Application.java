package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.ViewStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "_application")
public class Application extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    @JsonIgnore
    private Job job;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id",referencedColumnName = "id")
    @JsonIgnore
    private Resume resume;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }





    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
