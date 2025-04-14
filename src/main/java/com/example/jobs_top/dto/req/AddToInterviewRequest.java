package com.example.jobs_top.dto.req;

import java.util.List;

public class AddToInterviewRequest {
    private List<Long> applicationIds;

    public List<Long> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<Long> applicationIds) {
        this.applicationIds = applicationIds;
    }
}
