package com.example.jobs_top.dto.res;


import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Company;
import com.example.jobs_top.model.enums.AccountStatus;
import com.example.jobs_top.model.enums.RoleType;

public class AccountDto {
    private Long id;
    private String email;
    private String avatar;
    private String fullName;
    private AccountStatus status;
    private Long companyId;
    private String companyName;
    private RoleType role;


    public AccountDto(Account account, Company company) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.avatar = account.getAvatar();
        this.fullName = account.getFullName();
        this.status = account.getStatus();
        this.role = account.getRole();


        if (company != null) {
            this.companyId = company.getId();
            this.companyName = company.getName();
        }


    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
