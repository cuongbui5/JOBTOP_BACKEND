package com.example.jobs_top.model.enums;
//nếu mà user đã đồng ý phỏng vấn mà sau đó recruiter hủy phỏng vấn recruiter sẽ bị - 1 điểm uy tín , ngược lại nếu user hủy - user 1 điểm
//mỗi người sẽ có 10 điểm uy tín
public enum SlotStatus {
    COMPLETED("Đã hoàn thành"),
    NO_SHOW("Vắng mặt khi phỏng vấn");



    private final String label;

    SlotStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
