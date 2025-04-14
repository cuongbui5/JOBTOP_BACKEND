package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

public class JobDto {
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
    private String city;
    private Integer views;

    private CompanyDto company=new CompanyDto();


    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }



    public JobDto(Job job) {
        BeanUtils.copyProperties(job, this);
        if(job.getCompany()!=null){
            BeanUtils.copyProperties(job.getCompany(), company);
        }




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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}
