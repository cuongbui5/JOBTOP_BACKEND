package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.InterviewScheduleStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;

import java.time.LocalDate;
import java.time.LocalTime;

public interface InterviewScheduleView {
     LocalDate getInterviewDate(); // Lưu ngày phỏng vấn
     LocalTime getStartTime();
     LocalTime getEndTime();
     InterviewScheduleStatus getStatus();
     Long getCreatedBy();
     String getInterviewNote();
     String getOfficeAddress();
     String getJobTitle();
     Long getJobId();
}
