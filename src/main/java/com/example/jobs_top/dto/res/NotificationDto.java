package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Notification;

import java.time.ZonedDateTime;

public class NotificationDto {
    private Long id;
    private String content;
    private String sender;
    private ZonedDateTime createdAt;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.sender = notification.getSender();
        this.createdAt = notification.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
