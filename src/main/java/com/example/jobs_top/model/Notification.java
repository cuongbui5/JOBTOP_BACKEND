package com.example.jobs_top.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity{
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private Account account;
}
