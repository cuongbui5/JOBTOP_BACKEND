package com.example.jobs_top.model;

import jakarta.persistence.*;

@Entity
@Table(name = "_notification")
public class Notification extends BaseEntity{
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
}
