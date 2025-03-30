package com.example.jobs_top.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "_message")
public class Message extends BaseEntity{
    private String content;

}
