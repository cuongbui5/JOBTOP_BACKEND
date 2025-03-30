package com.example.jobs_top.model.enums;
//nếu mà user đã đồng ý phỏng vấn mà sau đó recruiter hủy phỏng vấn recruiter sẽ bị - 1 điểm uy tín , ngược lại nếu user hủy - user 1 điểm
//mỗi người sẽ có 10 điểm uy tín
public enum InterviewStatus {
    SCHEDULED("Đã lên lịch"),
    CONFIRMED("Đã xác nhận"),
    COMPLETED("Đã hoàn thành"),
    CANCELED("Đã hủy"),
    RESCHEDULED("Đã dời lịch");

    private final String label;

    InterviewStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
