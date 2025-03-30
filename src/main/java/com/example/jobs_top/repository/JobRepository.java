package com.example.jobs_top.repository;

import com.example.jobs_top.dto.res.JobCountDto;
import com.example.jobs_top.dto.res.RecruiterJobCountDto;
import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.dto.view.JobDetailView;
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
    Page<Job> findByRecruiterProfileId(Long recruiterProfileId, Pageable pageable);
    @EntityGraph(attributePaths = {"recruiterProfile.category", "industry", "tags"})
    <T> Optional<T> findById(Long id,Class<T> type);
    @Query("""
        SELECT j.id AS id, j.title AS title, j.requirements AS requirements, j.status AS status,
               j.salaryMin AS salaryMin, j.salaryMax AS salaryMax, 
               r.companyName AS companyName, j.city AS city, 
               j.jobType AS jobType, j.experienceLevel AS experienceLevel
        FROM Job j
        JOIN j.recruiterProfile r
        WHERE j.status = :status
    """)
    Page<JobCardView> findByStatus(@Param("status") JobStatus status, Pageable pageable);


    @Query("""
        SELECT j.id AS id, j.title AS title, j.requirements AS requirements, j.status AS status,
               j.salaryMin AS salaryMin, j.salaryMax AS salaryMax, 
               r.companyName AS companyName, j.city AS city, 
               j.jobType AS jobType, j.experienceLevel AS experienceLevel
        FROM Job j
        JOIN j.recruiterProfile r
    """)
    Page<JobCardView> findAllJobs(Pageable pageable);



    @Query(value = "SELECT j.id AS id, j.title AS title, j.requirements AS requirements, j.status AS status, " +
            "j.salary_min AS salaryMin, j.salary_max AS salaryMax, " +
            "r.company_name AS companyName, j.city AS city, " +
            "j.job_type AS jobType, j.experience_level AS experienceLevel " +
            "FROM job j " +
            "JOIN _recruiter_profile r ON j.recruiter_profile_id = r.id " +
            "JOIN _favorite_job f ON j.id = f.job_id " +
            "WHERE f.user_id = :userId", nativeQuery = true)
    List<JobCardView> findFavoriteJobsByUserId(@Param("userId") Long userId);




    Job findByTitleAndRecruiterProfileCompanyName(String title, String recruiterProfileCompanyName);
    @Query(value = """
      SELECT j.city, COUNT(j.id) AS job_count
                     FROM _job j
                     WHERE j.status = 'APPROVED'
                     AND j.application_deadline > CURRENT_DATE
                     GROUP BY j.city
    """, nativeQuery = true)
    List<Object[]> countJobsByLocation();
    /*List<JobCountDto> countJobsByIndustry();



    List<RecruiterJobCountDto> countJobsByRecruiter();*/


    @Query("SELECT j.id AS id,j.title AS title, j.requirements AS requirements, j.salaryMin AS salaryMin, j.status AS status, " +
            "j.salaryMax AS salaryMax, r.companyName AS companyName, j.city AS city, j.jobType AS jobType, " +
            "j.experienceLevel AS experienceLevel " +
            "FROM Job j " +
            "JOIN j.recruiterProfile r "+
            "WHERE j.status = 'APPROVED' AND j.applicationDeadline > CURRENT_DATE AND " +
            "(:updatedAfter IS NULL OR j.updatedAt >= :updatedAfter) AND " +
            "(:salaryMin IS NULL OR :salaryMax IS NULL OR (j.salaryMin >= :salaryMin AND j.salaryMin <= :salaryMax)) AND " +
            "(:exp IS NULL OR j.experienceLevel = :exp) AND " +
            "(:jobType IS NULL OR j.jobType = :jobType) AND " +
            "(:companyId IS NULL OR r.id = :companyId) AND " +
            "(:industryId IS NULL OR j.industry.id = :industryId) AND " +
            "(:keyword IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
            "(:city IS NULL OR j.city = :city)"
    )
    Page<JobCardView> findAllWithFilters(
            @Param("updatedAfter") ZonedDateTime updatedAfter,
            @Param("salaryMin") Integer salaryMin,
            @Param("salaryMax") Integer salaryMax,
            @Param("exp") ExperienceLevel exp,
            @Param("jobType") JobType jobType,
            @Param("companyId") Long companyId,
            @Param("industryId") Long industryId,
            @Param("keyword") String keyword,
            @Param("city") String city,
            Pageable pageable
    );

    @Query("SELECT j.id AS id, j.title AS title, j.requirements AS requirements, j.salaryMin AS salaryMin, j.status AS status, " +
            "j.salaryMax AS salaryMax, r.companyName AS companyName, j.city AS city, j.jobType AS jobType, " +
            "j.experienceLevel AS experienceLevel " +
            "FROM Job j " +
            "JOIN j.recruiterProfile r " +
            "WHERE j.id <> :jobId " +
            "AND (j.industry.id = :industryId " +
            "OR j.city = :city " +
            "OR j.jobType = :jobType " +
            "OR j.experienceLevel = :experienceLevel)")
    Page<JobCardView> findRelatedJobs(@Param("jobId") Long jobId,
                                      @Param("industryId") Long industryId,
                                      @Param("city") String city,
                                      @Param("jobType") JobType jobType,
                                      @Param("experienceLevel") ExperienceLevel experienceLevel,
                                      Pageable pageable);




}