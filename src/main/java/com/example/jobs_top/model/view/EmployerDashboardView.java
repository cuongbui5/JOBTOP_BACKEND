package com.example.jobs_top.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employer_dashboard_view")
public class EmployerDashboardView {
    @Id
    private Long accountId;
    private Long totalJobs;
    private Long activeJobs;
    private Long totalApplications;
    private Long approvedApplications;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Long getActiveJobs() {
        return activeJobs;
    }

    public void setActiveJobs(Long activeJobs) {
        this.activeJobs = activeJobs;
    }

    public Long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Long getApprovedApplications() {
        return approvedApplications;
    }

    public void setApprovedApplications(Long approvedApplications) {
        this.approvedApplications = approvedApplications;
    }
}
