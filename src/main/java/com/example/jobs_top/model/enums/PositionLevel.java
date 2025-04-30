package com.example.jobs_top.model.enums;

public enum PositionLevel {
    INTERN("Thực tập sinh"),
    FRESHER("Mới tốt nghiệp / Thực tập"),
    JUNIOR("Nhân viên"),
    MID("Nhân viên có kinh nghiệm"),
    SENIOR("Chuyên viên cao cấp"),
    LEAD("Trưởng nhóm"),
    MANAGER("Quản lý"),
    DIRECTOR("Giám đốc"),
    OTHER("Khác");

    private final String label;

    PositionLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
