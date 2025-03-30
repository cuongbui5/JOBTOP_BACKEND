package com.example.jobs_top.model.enums;

public enum JobStatus {
    PENDING("Chờ duyệt"),
    APPROVED("Đã duyệt"),
    REJECTED("Từ chối");

    private final String label;

    JobStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
