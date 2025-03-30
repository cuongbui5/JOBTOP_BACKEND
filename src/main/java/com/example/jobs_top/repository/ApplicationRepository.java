package com.example.jobs_top.repository;

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
    /*@EntityGraph(attributePaths = {"userProfile.user", "job.recruiterProfile"})
    @Query("SELECT a FROM Application a " +
            "JOIN a.job j " +
            "JOIN j.recruiterProfile r " +
            "JOIN a.userProfile u " +
            "JOIN u.user usr " +
            "WHERE r.companyName LIKE %:companyName% " +
            "AND j.title LIKE %:jobName% " +
            "AND usr.username LIKE %:username% " +
            "ORDER BY a.createdAt DESC limit 1")
    Application findLatestApplicationByCriteria(
            @Param("companyName") String companyName,
            @Param("jobName") String jobName,
            @Param("username") String username
    );*/



    @Query("""
        SELECT a.id AS id,
               j.id AS jobId,
               j.title AS jobTitle,
               j.city AS city,
               j.location AS location,
               j.salaryMin AS salaryMin,
               j.salaryMax AS salaryMax,
               j.experienceLevel as experienceLevel,
               j.recruiterProfile.companyName AS companyName,
               j.recruiterProfile.id AS recruiterProfileId,
               a.resume AS resume,
               a.status AS status,
               a.createdAt AS createdAt,
               a.updatedAt AS updatedAt
        FROM Application a
        JOIN a.job j
        WHERE a.userId = :userId
        ORDER BY a.createdAt DESC
    """)
    List<ApplicationUserView> findAppliedJobsByUser(@Param("userId") Long userId);

    Optional<Application> findByJobIdAndUserIdOrderByCreatedAtDesc(Long jobId, Long userId);


    @Query("""
        SELECT a.id AS id,
               j.id AS jobId,
               j.title AS jobTitle,
               j.applicationDeadline AS applicationDeadline,
               a.userId AS userId,
               a.resume AS resume,
               a.status AS status,
               a.createdAt AS createdAt,
               a.updatedAt AS updatedAt
        FROM Application a
        JOIN a.job j
        WHERE j.recruiterProfile.id = :recruiterId AND (:status IS NULL OR a.status = :status)
        ORDER BY a.createdAt DESC
    """)
    Page<ApplicationRecruiterView> findAppliedJobsByRecruiterProfileId(@Param("recruiterId") Long recruiterId, @Param("status")ApplicationStatus status, Pageable pageable);


}