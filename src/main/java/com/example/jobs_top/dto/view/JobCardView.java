package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobType;
import org.springframework.beans.factory.annotation.Value;

public interface JobCardView {
    Long getId();
    String getTitle();
    String getCity();
    String getRequirements();
    String getCompanyName();
    Integer getSalaryMin();
    Integer getSalaryMax();
    String getStatus();
    String getJobType();
    String getExperienceLevel();
}
