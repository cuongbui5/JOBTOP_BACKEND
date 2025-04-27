package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.enums.JobStatus;
import org.springframework.beans.factory.annotation.Value;

public interface JobStatusCountView {
    @Value("#{target.status.label}")
    String getStatus();
    Long getCount();
}
