package com.example.jobs_top.repository;

import com.example.jobs_top.dto.res.JobCountDto;
import com.example.jobs_top.dto.res.RecruiterJobCountDto;
import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.dto.view.JobDetailView;
import com.example.jobs_top.dto.view.JobStatusCountView;
import com.example.jobs_top.dto.view.JobTitleView;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.JobStatus;
import com.example.jobs_top.model.enums.JobType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.status = :status WHERE j.id = :jobId")
    int updateJobStatus(Long jobId, JobStatus status);

    @EntityGraph(attributePaths = {"company"})
    Optional<Job> findById(Long id);



    @EntityGraph(attributePaths = {"company"})
    @Query("""
        SELECT j
        FROM Job j
        WHERE (:status IS NULL OR j.status = :status)
        AND (:createdBy IS NULL OR j.createdBy = :createdBy)
    """)
    Page<Job> findAllJobs(Pageable pageable,@Param("status") JobStatus status,@Param("createdBy") Long createdBy);



    /*@Query(value = "SELECT j.id AS id, j.title AS title, j.requirements AS requirements, j.status AS status, " +
            "j.salary_min AS salaryMin, j.salary_max AS salaryMax, " +
            "r.company_name AS companyName, j.city AS city, " +
            "j.job_type AS jobType, j.experience_level AS experienceLevel " +
            "FROM job j " +
            "JOIN _recruiter_profile r ON j.recruiter_profile_id = r.id " +
            "JOIN _favorite_job f ON j.id = f.job_id " +
            "WHERE f.account_id = :accountId", nativeQuery = true)
    List<JobCardView> findFavoriteJobsByUserId(@Param("accountId") Long accountId);*/





    @Query(value = """
      SELECT j.city, COUNT(j.id) AS job_count
                     FROM jobs j
                     WHERE j.status = 'APPROVED'
                     AND j.application_deadline > CURRENT_DATE
                     GROUP BY j.city
    """, nativeQuery = true)
    List<Object[]> countJobsByLocation();



    @Query("SELECT j.id AS id, j.title AS title, j.requirements AS requirements, j.salaryMin AS salaryMin, j.status AS status, " +
            "j.salaryMax AS salaryMax, r.name AS companyName, j.city AS city, j.jobType AS jobType, " +
            "j.experienceLevel AS experienceLevel, j.views AS views " +
            "FROM Job j " +
            "JOIN j.company r " +
            "JOIN r.category c " +
            "WHERE j.status = 'APPROVED' AND j.applicationDeadline > CURRENT_DATE AND " +
            "(:categoryIds IS NULL OR c.id IN :categoryIds) AND " +
            "(:salaryMin IS NULL OR :salaryMax IS NULL OR (j.salaryMin >= :salaryMin AND j.salaryMin <= :salaryMax)) AND " +
            "(:exps IS NULL OR j.experienceLevel IN :exps) AND " +
            "(:jobTypes IS NULL OR j.jobType IN :jobTypes) AND " +
            "(:companyIds IS NULL OR r.id IN :companyIds) AND " +
            "(:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:cities IS NULL OR j.city IN :cities)"
    )
    Page<JobCardView> findAllWithFilters(
            @Param("categoryIds") List<Long> categoryIds,
            @Param("salaryMin") Integer salaryMin,
            @Param("salaryMax") Integer salaryMax,
            @Param("exps") List<ExperienceLevel> exps,
            @Param("jobTypes") List<JobType> jobTypes,
            @Param("companyIds") List<Long> companyIds,
            @Param("keyword") String keyword,
            @Param("cities") List<String> cities,
            Pageable pageable
    );

    @Query("""
             SELECT j.id AS id, j.title AS title FROM Job j
             WHERE j.status = 'APPROVED' and j.createdBy=:accountId
    """)
    List<JobTitleView> getAllJobsTitle(@Param("accountId") Long accountId);

    @Query("SELECT j.status AS status, COUNT(j) AS count FROM Job j GROUP BY j.status")
    List<JobStatusCountView> countJobsByStatus();

    @Query("SELECT j FROM Job j ORDER BY j.views DESC")
    List<Job> findTopViewedJobs(Pageable pageable);

}