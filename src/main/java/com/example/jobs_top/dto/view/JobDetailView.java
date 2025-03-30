package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.Industry;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.Tag;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    Industry getIndustry();
    String getCity();
    @Value("#{target.recruiterProfile.companyName}")
    String getCompanyName();
    @Value("#{target.recruiterProfile.id}")
    String getRecruiterProfileId();
    Set<Tag> getTags();

}
