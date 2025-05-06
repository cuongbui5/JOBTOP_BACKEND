package com.example.jobs_top.dto.req;

public class UpdateAccountRequest {
    private String fullName;
    private String avatar;
    private String currentPassword;
    private String newPassword;
    private Boolean receiveEmail;

    public Boolean getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(Boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
