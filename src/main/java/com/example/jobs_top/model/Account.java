package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.AccountStatus;
import com.example.jobs_top.model.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;




@Table(name = "accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"})
        })
@Entity
public class Account extends BaseEntity {
    private String fullName;
    private String avatar;
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private Long resumeDefault;

    public Long getResumeDefault() {
        return resumeDefault;
    }

    public void setResumeDefault(Long resumeDefault) {
        this.resumeDefault = resumeDefault;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
