package com.example.jobs_top.dto.view;



public interface FavoriteJobView extends JobCardView{
    Long getId();
    Long getJobId();
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
