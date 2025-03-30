package com.example.jobs_top.dto.res;

public class RecruiterJobCountDto {
    private Long recruiterId;
    private String companyName;
    private Long jobCount;

    public RecruiterJobCountDto(Long recruiterId, String companyName, Long jobCount) {
        this.recruiterId = recruiterId;
        this.companyName = companyName;
        this.jobCount = jobCount;
    }

    public Long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getJobCount() {
        return jobCount;
    }

    public void setJobCount(Long jobCount) {
        this.jobCount = jobCount;
    }
}
