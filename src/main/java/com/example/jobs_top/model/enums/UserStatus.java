package com.example.jobs_top.model.enums;

public enum UserStatus {
    ACTIVE("Đang hoạt động"),
    BANNED ("Khóa vĩnh viễn");
    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
