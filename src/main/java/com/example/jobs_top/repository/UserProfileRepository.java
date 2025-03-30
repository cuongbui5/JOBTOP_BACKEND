package com.example.jobs_top.repository;

import com.example.jobs_top.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
  Optional<UserProfile> findByUserId(Long userId);
  UserProfile findByFullName(String fullName);

}