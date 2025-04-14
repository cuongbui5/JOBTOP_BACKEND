package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.InterviewStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "interview_schedules")
public class InterviewSchedule extends BaseEntity{
    private LocalDate interviewDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
    private Long createdBy;


    @Lob
    private String interviewNote;

    private String officeAddress;


    public InterviewStatus getStatus() {
        return status;
    }

    public void setStatus(InterviewStatus status) {
        this.status = status;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getInterviewNote() {
        return interviewNote;
    }

    public void setInterviewNote(String interviewNote) {
        this.interviewNote = interviewNote;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
