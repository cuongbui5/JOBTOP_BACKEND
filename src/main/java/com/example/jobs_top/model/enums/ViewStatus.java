package com.example.jobs_top.model.enums;

public enum ViewStatus {
    UNSEEN("Chưa xem"),
    SEEN("Đã xem");

    private final String label;

    ViewStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
