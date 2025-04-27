package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.PlanUsageCountView;
import com.example.jobs_top.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByIsActiveTrue();

    @Query("SELECT p.id AS planId, p.name AS planName, COUNT(ap) AS usageCount " +
            "FROM AccountPlan ap " +
            "JOIN ap.plan p " +
            "GROUP BY p.id " +
            "ORDER BY usageCount DESC")
    List<PlanUsageCountView> countPlanUsage();

}