package com.example.jobs_top.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "_package")
public class Package extends BaseEntity{
    private String name;
    private Double price;
    private String description;
}
