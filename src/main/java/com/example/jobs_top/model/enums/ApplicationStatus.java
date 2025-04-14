package com.example.jobs_top.model.enums;

public enum ApplicationStatus {
    PENDING("Đang chờ xử lý"),
    VIEWED("Đã xem"),
    APPROVED("Được chấp nhận"),
    ADDED_TO_INTERVIEW("Đã thêm vào phỏng vấn"),
    COMPLETED("Đã hoàn thành"),
    NO_SHOW("Vắng mặt khi phỏng vấn"),
    REJECTED("Bị từ chối");


    private final String label;

    ApplicationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
