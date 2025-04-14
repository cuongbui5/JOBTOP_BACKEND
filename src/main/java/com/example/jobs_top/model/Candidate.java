package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "candidates")
@Entity
public class Candidate extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String phone;

    private LocalDate dateOfBirth;

    private String address;

    @Column(length = 2000)
    private String skills;


    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String education;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Boolean publicProfile = true;

    @Column(length = 2000)
    private String workExperience;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private Account account;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(Boolean publicProfile) {
        this.publicProfile = publicProfile;
    }



    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }




}
