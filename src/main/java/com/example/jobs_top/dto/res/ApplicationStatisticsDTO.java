package com.example.jobs_top.dto.res;

import java.util.List;

public class ApplicationStatisticsDTO {
    private Long jobId;
    private long total;
    private List<ApplicationStatusStatsDTO> statuses;

    public ApplicationStatisticsDTO(Long jobId, long total, List<ApplicationStatusStatsDTO> statuses) {
        this.jobId = jobId;
        this.total = total;
        this.statuses = statuses;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ApplicationStatusStatsDTO> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ApplicationStatusStatsDTO> statuses) {
        this.statuses = statuses;
    }
}
