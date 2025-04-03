package com.example.jobs_top.dto.view;

import jakarta.persistence.Lob;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public interface ReviewView {
    Long getId();
    int getRating();
    String getComment();
    String getEmail();
    LocalDate getInterviewDate();
    ZonedDateTime getUpdatedAt();
}
