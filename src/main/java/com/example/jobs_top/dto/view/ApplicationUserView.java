package com.example.jobs_top.dto.view;


import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.model.Resume;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.ExperienceLevel;
import java.time.ZonedDateTime;

public interface ApplicationUserView {
    Long getId();
    Long getJobId();
    String getCompanyName();
    String getJobTitle();
    String getCity();
    String getLocation();
    Integer getSalaryMin();
    Integer getSalaryMax();
    Resume getResume();
    InterviewSchedule getInterviewSchedule();
    ExperienceLevel getExperienceLevel();
    ApplicationStatus getStatus();
    ZonedDateTime getCreatedAt();
    ZonedDateTime getUpdatedAt();

}
