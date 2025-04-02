package com.example.jobs_top.model.enums;

public enum InterviewScheduleStatus {
    SCHEDULED("Đã lên lịch"),
    CANCELED_BY_RECRUITER("Nhà tuyển dụng đã hủy"),
    COMPLETED("Đã hoàn thành");

    private final String label;

    InterviewScheduleStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
