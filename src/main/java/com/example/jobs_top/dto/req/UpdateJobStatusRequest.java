package com.example.jobs_top.dto.req;

import com.example.jobs_top.model.enums.JobStatus;

public class UpdateJobStatusRequest {
    private JobStatus status;

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
