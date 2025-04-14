package com.example.jobs_top.dto.req;



public class CreateReview {
    private Long interviewScheduleId;
    private int rating;
    private String comment;
    private Long jobId;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getInterviewScheduleId() {
        return interviewScheduleId;
    }

    public void setInterviewScheduleId(Long interviewScheduleId) {
        this.interviewScheduleId = interviewScheduleId;
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
