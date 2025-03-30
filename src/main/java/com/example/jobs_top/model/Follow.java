package com.example.jobs_top.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "_follow")
public class Follow extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id",referencedColumnName = "id")
    @JsonIgnore
    private RecruiterProfile recruiter;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RecruiterProfile getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(RecruiterProfile recruiter) {
        this.recruiter = recruiter;
    }
}
