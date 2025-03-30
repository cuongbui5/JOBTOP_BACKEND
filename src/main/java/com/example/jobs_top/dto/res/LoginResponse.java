package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.User;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

public class LoginResponse{
    private User user;
    private String token;

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
