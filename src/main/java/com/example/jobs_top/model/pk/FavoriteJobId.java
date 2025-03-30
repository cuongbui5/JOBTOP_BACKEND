package com.example.jobs_top.model.pk;

import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteJobId implements Serializable {
    @Column(name = "job_id")
    private Long jobId;
    @Column(name = "user_id")
    private Long userId;

    public FavoriteJobId() {
    }

    public FavoriteJobId(Long jobId, Long userId) {
        this.jobId = jobId;
        this.userId = userId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteJobId that = (FavoriteJobId) o;
        return Objects.equals(jobId, that.jobId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, userId);
    }
}
