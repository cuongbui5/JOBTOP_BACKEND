package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Industry;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.Tag;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class JobDetailResponse {
    private Long id;
    private String title;
    private String location;
    private String description;
    private String requirements;
    private String benefits;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private JobStatus status;
    private Integer salaryMin;
    private Integer salaryMax;
    private LocalDate applicationDeadline;
    private String workSchedule;
    private Industry industry;
    private String city;
    private RecruiterProfile recruiterProfile;
    private Set<Tag> tags = new HashSet<>();
    public JobDetailResponse(Job job) {
        BeanUtils.copyProperties(job, this);
    }
    public String getExperienceLevelLabel() {
        return experienceLevel != null ? experienceLevel.getLabel() : null;
    }
    public String getJobTypeLabel() {
        return jobType != null ? jobType.getLabel() : null;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }


    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
