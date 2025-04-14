package com.example.jobs_top.dto.view;



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
    Integer getViews();
}
