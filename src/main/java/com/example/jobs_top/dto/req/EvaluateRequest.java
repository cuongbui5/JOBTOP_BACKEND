package com.example.jobs_top.dto.req;

public class EvaluateRequest {
    private Long jobId;
    private Long resumeId;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long joId) {
        this.jobId = joId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }
}
