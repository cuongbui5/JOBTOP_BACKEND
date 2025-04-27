package com.example.jobs_top.dto.view;

import org.springframework.beans.factory.annotation.Value;

public interface ApplicationStatusCountView {
    @Value("#{target.status.label}")
    String getStatus();
    Long getCount();
}
