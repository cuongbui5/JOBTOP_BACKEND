package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity{
    private String title;
    private String location;
    private String city;
    @Lob
    private String description;
    @Lob
    private String requirements;
    @Lob
    private String benefits;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;
    @Enumerated(EnumType.STRING)
    private JobStatus status=JobStatus.PENDING;
    private Integer salaryMin;
    private Integer salaryMax;
    private LocalDate applicationDeadline;
    private String workSchedule;
    private Integer views=0;
    private Long createdBy;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    @JsonIgnore
    private Company company;


    public void increaseViews() {
        this.views++;
    }



    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }



    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
