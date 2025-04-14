package com.example.jobs_top.dto.req;

import com.example.jobs_top.model.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @Email
    private String email;
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 kí tự")
    private String password;
    private RoleType role;
    private String fullName;

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



    public void setEmail(@Email String email) {
        this.email = email;
    }

    public void setPassword(@Size(min = 6, message = "Mật khẩu phải có ít nhất 6 kí tự") String password) {
        this.password = password;
    }


}
