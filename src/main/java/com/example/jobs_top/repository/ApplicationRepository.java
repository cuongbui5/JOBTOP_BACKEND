package com.example.jobs_top.repository;

import com.example.jobs_top.dto.res.ApplicationStatusStatsDTO;
import com.example.jobs_top.dto.view.ApplicationRecruiterView;
import com.example.jobs_top.dto.view.ApplicationUserView;
import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByIdIn(List<Long> ids);
    List<Application> findByInterviewScheduleId(Long interviewScheduleId);


    @Query("""
        SELECT a.id AS id,
               j.id AS jobId,
               j.title AS jobTitle,
               j.city AS city,
               j.location AS location,
               j.salaryMin AS salaryMin,
               j.salaryMax AS salaryMax,
               j.experienceLevel as experienceLevel,
               j.company.name AS companyName,
               j.company.id AS companyId,
               a.resume AS resume,
               a.status AS status,
               a.createdAt AS createdAt,
               a.updatedAt AS updatedAt,
               a.interviewSchedule AS interviewSchedule
        FROM Application a
        JOIN a.job j
        LEFT JOIN a.interviewSchedule
        WHERE a.account.id = :accountId AND (:status IS NULL OR a.status = :status)
    """)
    Page<ApplicationUserView> findAppliedJobsByAccount(@Param("accountId") Long accountId,
                                                       @Param("status") ApplicationStatus applicationStatus,
                                                       Pageable pageable);

    Optional<Application> findByJobIdAndAccountIdOrderByCreatedAtDesc(Long jobId, Long accountId);

    @Query("""
        SELECT a.id AS id,
               j.id AS jobId,
               j.title AS jobTitle,
               j.applicationDeadline AS applicationDeadline,
               c.id AS accountId,
               c.email as email,
               a.resume AS resume,
               a.status AS status,
               a.createdAt AS createdAt,
               a.updatedAt AS updatedAt
        FROM Application a
        JOIN a.job j
        JOIN a.resume r
        JOIN a.account c
        WHERE j.company.id = :companyId AND
        (:status IS NULL OR a.status = :status) AND
        (:jobId IS NULL OR j.id = :jobId) AND
        j.applicationDeadline > CURRENT_DATE
    """)
    Page<ApplicationRecruiterView> findApplicationByFilter(@Param("companyId") Long companyId,
                                                           @Param("jobId") Long jobId,
                                                           @Param("status")ApplicationStatus status,
                                                           Pageable pageable);

    @Query("""
        SELECT a.id AS id,
               j.id AS jobId,
               j.title AS jobTitle,
               j.applicationDeadline AS applicationDeadline,
               c.id AS accountId,
               c.email as email,
               a.resume AS resume,
               a.status AS status,
               a.createdAt AS createdAt,
               a.updatedAt AS updatedAt
        FROM Application a
        JOIN a.job j
        JOIN a.resume r
        JOIN a.account c
        WHERE a.interviewSchedule.id = :scheduleId
    """)
    Page<ApplicationRecruiterView> findByScheduleId(@Param("scheduleId") Long scheduleId, Pageable pageable);

    @Query(value = "SELECT " +
            "CASE WHEN status IS NULL THEN 'TOTAL' ELSE status END AS status, " +
            "COUNT(*) AS count " +
            "FROM applications " +
            "WHERE job_id = :jobId " +
            "GROUP BY status WITH ROLLUP", nativeQuery = true)
    List<Object[]> countApplicationsByStatusWithTotal(@Param("jobId") Long jobId);




}