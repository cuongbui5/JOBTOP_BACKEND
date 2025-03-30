package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.InterviewStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "_interview_schedule")
public class InterviewSchedule extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    private LocalDateTime interviewTime;

    @Lob
    private String interviewNote;

    private String officeAddress;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }
}
