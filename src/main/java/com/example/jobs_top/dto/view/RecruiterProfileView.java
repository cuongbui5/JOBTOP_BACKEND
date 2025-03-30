package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.Category;
import com.example.jobs_top.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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
