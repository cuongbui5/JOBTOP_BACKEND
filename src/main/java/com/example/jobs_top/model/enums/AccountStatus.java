package com.example.jobs_top.model.enums;

public enum AccountStatus {
    ACTIVE("Đang hoạt động"),
    BANNED ("Khóa vĩnh viễn");
    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
