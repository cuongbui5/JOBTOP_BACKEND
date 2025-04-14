package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.ReportView;
import com.example.jobs_top.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("""
    SELECT r.id AS id,
        r.reason AS reason,
        r.additionalInfo AS additionalInfo,
        r.account.id as accountId,
        r.account.email AS email,
        r.job.title AS jobTitle,
        r.job.id AS jobId,
        r.createdAt AS createdAt
    FROM Report r
   
""")
    Page<ReportView> getAll(Pageable pageable);
}