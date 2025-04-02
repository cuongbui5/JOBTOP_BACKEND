package com.example.jobs_top.dto.req;

import java.util.List;

public class CreateManySlots {
    private Long interviewScheduleId;
    private List<Long> applicationIds;

    public Long getInterviewScheduleId() {
        return interviewScheduleId;
    }

    public void setInterviewScheduleId(Long interviewScheduleId) {
        this.interviewScheduleId = interviewScheduleId;
    }

    public List<Long> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<Long> applicationIds) {
        this.applicationIds = applicationIds;
    }
}
