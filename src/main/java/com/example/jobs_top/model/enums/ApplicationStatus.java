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
    public boolean canTransitionTo(ApplicationStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == VIEWED || newStatus == REJECTED;
            case VIEWED -> newStatus == APPROVED || newStatus == REJECTED;
            case APPROVED -> newStatus == ADDED_TO_INTERVIEW || newStatus == REJECTED;
            case ADDED_TO_INTERVIEW -> newStatus == COMPLETED || newStatus == NO_SHOW || newStatus == REJECTED;
            case NO_SHOW, COMPLETED, REJECTED -> false;
        };
    }
}
