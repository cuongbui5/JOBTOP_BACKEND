package com.example.jobs_top.model.enums;

public enum ApplicationStatus {
    PENDING("Đang chờ xử lý"),
    VIEWED("Đã xem"),
    APPROVED("Được chấp nhận"),
    REJECTED("Bị từ chối");


    private final String label;

    ApplicationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
