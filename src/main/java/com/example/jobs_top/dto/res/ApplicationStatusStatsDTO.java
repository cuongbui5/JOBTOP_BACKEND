package com.example.jobs_top.dto.res;


import com.example.jobs_top.model.enums.ApplicationStatus;

public class ApplicationStatusStatsDTO {
    private String status;
    private long count;

    public ApplicationStatusStatsDTO(String status, long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return ApplicationStatus.valueOf(status).getLabel();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
