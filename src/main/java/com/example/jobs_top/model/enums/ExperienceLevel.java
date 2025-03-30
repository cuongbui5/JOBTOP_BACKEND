package com.example.jobs_top.model.enums;

public enum ExperienceLevel {
    NO_REQUIREMENT("Không yêu cầu"),
    LESS_THAN_ONE_YEAR("Dưới 1 năm"),
    ONE_YEAR("1 năm"),
    TWO_YEARS("2 năm"),
    THREE_YEARS("3 năm"),
    FOUR_YEARS("4 năm"),
    FIVE_YEARS("5 năm"),
    MORE_THAN_FIVE_YEARS("Trên 5 năm");
    private final String label;

    ExperienceLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
