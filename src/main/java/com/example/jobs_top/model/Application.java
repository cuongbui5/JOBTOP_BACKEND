package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.ApplicationStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;



@Entity
@Table(name = "applications")
public class Application extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_schedule_id", referencedColumnName = "id")
    @JsonIgnore
    private InterviewSchedule interviewSchedule;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonIgnore
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public InterviewSchedule getInterviewSchedule() {
        return interviewSchedule;
    }

    public void setInterviewSchedule(InterviewSchedule interviewSchedule) {
        this.interviewSchedule = interviewSchedule;
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
