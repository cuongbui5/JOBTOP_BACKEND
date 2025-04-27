package com.example.jobs_top.model.enums;

public enum RoleType {
    CANDIDATE("Ứng viên"),
    EMPLOYER("Nhà tuyển dụng"),
    ADMIN("Quản trị viên");

    private final String label;

    RoleType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
