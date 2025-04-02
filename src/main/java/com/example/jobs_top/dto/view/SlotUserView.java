package com.example.jobs_top.dto.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public interface SlotUserView {
    Long getId();
    Long getApplicationId();
    Long getInterviewScheduleId();
    String getInterviewScheduleStatus();
    String getJobTitle();
    String getInterviewNote();
    LocalTime getStartTime();
    LocalTime getEndTime();
    LocalDate getInterviewDate();

    String getStatus();
    String getOfficeAddress();
    ZonedDateTime getUpdatedAt();

}
