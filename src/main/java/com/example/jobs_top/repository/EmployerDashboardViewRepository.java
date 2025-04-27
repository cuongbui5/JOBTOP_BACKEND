package com.example.jobs_top.repository;

import com.example.jobs_top.model.view.EmployerDashboardView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerDashboardViewRepository extends JpaRepository<EmployerDashboardView, Long> {
    Optional<EmployerDashboardView> findByAccountId(Long accountId);
}
