package com.example.jobs_top.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "_interview_review")
public class InterviewReview extends BaseEntity{
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_slot_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private InterviewSlot interviewSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User reviewer;
    private Long jobId;

    private int rating;

    @Lob
    private String comment;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public InterviewSlot getInterviewSlot() {
        return interviewSlot;
    }

    public void setInterviewSlot(InterviewSlot interviewSlot) {
        this.interviewSlot = interviewSlot;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
