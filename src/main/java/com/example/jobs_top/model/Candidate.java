package com.example.jobs_top.model;

import com.example.jobs_top.model.enums.EducationLevel;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.Gender;
import com.example.jobs_top.model.enums.PositionLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "candidates")
@Entity
public class Candidate extends BaseEntity{
    private String fullName;
    @Column(unique = true, nullable = false)
    private String phone;
    private LocalDate dateOfBirth;
    private String city;
    private String address;
    private String workLocation;
    @Enumerated(EnumType.STRING)
    private PositionLevel positionLevel;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private EducationLevel education;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private ExperienceLevel workExperience;
    private String desiredPosition;
    private String expectedSalary;

    private Boolean searchable = true;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private Account account;

    public String getWorkLocation() {
        return workLocation;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }



    public PositionLevel getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(PositionLevel positionLevel) {
        this.positionLevel = positionLevel;
    }

    public void setWorkExperience(ExperienceLevel workExperience) {
        this.workExperience = workExperience;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public EducationLevel getEducation() {
        return education;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEducation(EducationLevel education) {
        this.education = education;
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

    public ExperienceLevel getWorkExperience() {
        return workExperience;
    }

    public Boolean getSearchable() {
        return searchable;
    }

    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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






}
