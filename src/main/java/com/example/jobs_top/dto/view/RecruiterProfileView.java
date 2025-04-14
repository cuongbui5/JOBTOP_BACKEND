package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.Category;

public interface RecruiterProfileView {
    Long getId();
    String getCompanyName();
    String getCompanyLogo();
    String getDescription();
    String getNation();
    String getCompanyAddress();
    String getCompanySize();
    String getCompanyWebsite();
    Category getCategory();
}
