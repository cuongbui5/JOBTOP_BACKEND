package com.example.jobs_top.dto.view;

import java.time.ZonedDateTime;

public interface MessageView {
    Long getId();
    String getContent();
    Long getSenderId();
    ZonedDateTime getCreatedAt();

}
