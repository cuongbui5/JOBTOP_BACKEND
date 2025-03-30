package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Table(name = "_user_profile")
@Entity
public class UserProfile extends BaseEntity{
    private String fullName;
    private String image;
    private LocalDate dateOfBirth;
    private String address;
    private String skills;
    private String phone;
    @Column(length = 1000)
    private String description;
    @Column(length = 1000)
    private String education;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean isPublicProfile = true;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getPublicProfile() {
        return isPublicProfile;
    }

    public void setPublicProfile(Boolean publicProfile) {
        isPublicProfile = publicProfile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserProfile() {

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



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
