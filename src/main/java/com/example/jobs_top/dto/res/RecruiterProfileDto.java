package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Company;
import org.springframework.beans.BeanUtils;

public class RecruiterProfileDto {
    private Long id;
    private String companyName;
    private String companyLogo;
    private String description;
    private String nation;
    private String companyAddress;
    private String companySize;
    private String companyWebsite;

    public RecruiterProfileDto(Company recruiterProfile) {
        BeanUtils.copyProperties(recruiterProfile, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
