package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.JobStatus;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface JobDetailView {
    Long getId();
    String getTitle();
    String getLocation();
    String getDescription();
    String getRequirements();
    String getBenefits();
    String getJobType();
    JobStatus getStatus();
    String getExperienceLevel();
    Integer getSalaryMin();
    Integer getSalaryMax();
    LocalDate getApplicationDeadline();
    String getWorkSchedule();

    String getCity();
    @Value("#{target.recruiterProfile.companyName}")
    String getCompanyName();
    @Value("#{target.recruiterProfile.id}")
    String getRecruiterProfileId();


}
