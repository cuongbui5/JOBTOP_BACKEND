package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.InterviewStatus;
import org.springframework.beans.factory.annotation.Value;

public interface InterviewStatusCountView {
    @Value("#{target.status.label}")
    String getStatus();
    Long getCount();
}
