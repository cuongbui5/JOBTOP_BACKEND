package com.example.jobs_top.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    private String name;


    public Category(String name) {
        this.name = name;
    }

    public Category() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
