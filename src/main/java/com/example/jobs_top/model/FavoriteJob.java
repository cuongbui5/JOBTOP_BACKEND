package com.example.jobs_top.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "favorite_jobs")
public class FavoriteJob extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonIgnore
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id",referencedColumnName = "id")
    @JsonIgnore
    private Job job;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
