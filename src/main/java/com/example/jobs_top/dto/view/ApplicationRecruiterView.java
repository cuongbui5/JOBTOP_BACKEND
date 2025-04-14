package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.Resume;
import com.example.jobs_top.model.enums.ApplicationStatus;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface ApplicationRecruiterView {
    Long getId();
    Long getJobId();
    Long getUserId();
    String
    getEmail();
    ApplicationStatus getStatus();
    Resume getResume();
    String getJobTitle();
    LocalDate getApplicationDeadline();
    ZonedDateTime getCreatedAt();
    ZonedDateTime getUpdatedAt();
}
