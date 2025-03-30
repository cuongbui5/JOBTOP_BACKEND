package com.example.jobs_top.dto.view;

import org.springframework.beans.factory.annotation.Value;

public interface FavoriteJobView extends JobCardView{
    Long getId();
    Long getJobId();
    String getTitle();
    String getCity();
    String getRequirements();
    String getCompanyName();
    Integer getSalaryMin();
    Integer getSalaryMax();
    @Value("#{target.status.label}")
    String getStatus();
    @Value("#{target.jobType.label}")
    String getJobType();
    @Value("#{target.experienceLevel.label}")
    String getExperienceLevel();



}
