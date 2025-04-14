package com.example.jobs_top.dto.view;

import com.example.jobs_top.model.Resume;

public interface SlotView {
    Long getId();
    String getEmail();

    Resume getResume();
    String getStatus();
    String getJobId();
    String getJobTitle();

}
