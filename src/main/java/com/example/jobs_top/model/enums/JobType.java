package com.example.jobs_top.model.enums;

public enum JobType {
    ALL("Tất cả"),
    FULL_TIME("Toàn thời gian"),
    PART_TIME("Bán thời gian"),
    INTERNSHIP("Thực tập"),
    OTHER("Khác");

    private final String label;

    JobType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
