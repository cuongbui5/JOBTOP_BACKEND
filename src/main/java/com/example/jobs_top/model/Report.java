package com.example.jobs_top.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "reports")
public class Report extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonIgnore
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    @JsonIgnore
    private Job job;
    private String reason;
    private String additionalInfo;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
