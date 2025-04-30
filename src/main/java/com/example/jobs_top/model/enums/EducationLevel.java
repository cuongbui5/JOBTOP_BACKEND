package com.example.jobs_top.model.enums;

public enum EducationLevel {
    HIGH_SCHOOL("Tốt nghiệp THPT"),
    COLLEGE("Cao đẳng"),
    UNIVERSITY("Đại học"),
    BACHELOR("Cử nhân"),
    MASTER("Thạc sĩ"),
    DOCTOR("Tiến sĩ"),
    OTHER("Khác");

    private final String label;

    EducationLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
