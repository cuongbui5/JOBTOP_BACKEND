package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.model.enums.InterviewStatus;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class InterviewScheduleDto {
    private Long id;
    private String jobTitle;
    private String companyName;
    private LocalDate interviewDate;
    private String timeRange;
    private String status;
    private String interviewNote;
    private String officeAddress;

    public InterviewScheduleDto(Application application) {
        InterviewSchedule interviewSchedule = application.getInterviewSchedule();
        this.id=interviewSchedule.getId();
        this.jobTitle=application.getJob().getTitle();
        this.companyName=application.getJob().getCompany().getName();
        this.interviewDate=interviewSchedule.getInterviewDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.timeRange = interviewSchedule.getStartTime().format(formatter)
                + " - " + interviewSchedule.getEndTime().format(formatter);

        this.status=interviewSchedule.getStatus().getLabel();
        this.interviewNote=interviewSchedule.getInterviewNote();
        this.officeAddress=interviewSchedule.getOfficeAddress();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
