package com.example.jobs_top.dto.res;


import com.example.jobs_top.model.Account;

public class AccountDto {
    private Long id;
    private String email;
    private String avatar;
    private String fullName;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.avatar = account.getAvatar();
        this.fullName = account.getFullName();
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
}
