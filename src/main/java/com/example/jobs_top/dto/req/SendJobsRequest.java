package com.example.jobs_top.dto.req;

import java.util.List;

public class SendJobsRequest {
    private List<Long> jobIds;
    private Long candidateId;

    public List<Long> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<Long> jobIds) {
        this.jobIds = jobIds;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
