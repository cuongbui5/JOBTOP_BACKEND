package com.example.jobs_top.model;

import jakarta.persistence.*;


@Entity
@Table(name = "_role")
public class Role extends BaseEntity{

    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
