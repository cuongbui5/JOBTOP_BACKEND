package com.example.jobs_top.repository;

import com.example.jobs_top.model.view.JobStatisticsView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobStatisticsViewRepository extends JpaRepository<JobStatisticsView, Long> {
    Optional<JobStatisticsView> findByJobId(Long jobId);
}
