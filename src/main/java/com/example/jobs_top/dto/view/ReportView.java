package com.example.jobs_top.dto.view;

import java.time.ZonedDateTime;

public interface ReportView {
    Long getId();
    Long getUserId();
    Long getJobId();
    String getEmail();
    String getJobTitle();
    String getReason();
    String getAdditionalInfo();
    ZonedDateTime getCreatedAt();
}
