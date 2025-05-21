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
            case PENDING -> newStatus == VIEWED || newStatus == REJECTED || newStatus == APPROVED;
            case VIEWED -> newStatus == APPROVED || newStatus == REJECTED;
            case APPROVED -> newStatus == ADDED_TO_INTERVIEW || newStatus == REJECTED;
            case ADDED_TO_INTERVIEW -> newStatus == COMPLETED || newStatus == REJECTED;
            case COMPLETED -> newStatus == NO_SHOW;
            case NO_SHOW, REJECTED -> false;
        };
    }
}
