package com.example.jobs_top.dto.req;

import com.example.jobs_top.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @Email
    private String email;
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 kí tự")
    private String password;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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
