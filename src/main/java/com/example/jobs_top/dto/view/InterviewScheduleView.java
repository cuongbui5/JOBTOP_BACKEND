package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.InterviewStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public interface InterviewScheduleView {
     LocalDate getInterviewDate(); // Lưu ngày phỏng vấn
     LocalTime getStartTime();
     LocalTime getEndTime();
     InterviewStatus getStatus();
     Long getCreatedBy();
     String getInterviewNote();
     String getOfficeAddress();
     String getJobTitle();
     Long getJobId();
}
