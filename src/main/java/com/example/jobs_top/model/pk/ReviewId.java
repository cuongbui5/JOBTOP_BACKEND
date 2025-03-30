package com.example.jobs_top.model.pk;

import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class ReviewId {
    @ManyToOne
    @JoinColumn(name = "recruiter_profile_id", referencedColumnName = "id")
    private RecruiterProfile recruiterProfile;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewId reviewId = (ReviewId) o;
        return Objects.equals(recruiterProfile, reviewId.recruiterProfile) && Objects.equals(user, reviewId.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recruiterProfile, user);
    }

    public void setRecruiterProfile(RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
