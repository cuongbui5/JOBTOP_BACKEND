package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.Resume;
import jakarta.persistence.Lob;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface ApplicationUserView {
    Long getId();
    Long getJobId();
    Long getRecruiterProfileId();
    String getCompanyName();
    String getJobTitle();
    String getCity();
    String getLocation();
    Integer getSalaryMin();
    Integer getSalaryMax();
    Resume getResume();
    @Value("#{target.experienceLevel.label}")
    String getExperienceLevel();
    @Value("#{target.status.label}")
    String getStatus();

    ZonedDateTime getCreatedAt();
    ZonedDateTime getUpdatedAt();

}
