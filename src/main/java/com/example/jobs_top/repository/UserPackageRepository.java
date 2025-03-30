package com.example.jobs_top.repository;

import com.example.jobs_top.model.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
}