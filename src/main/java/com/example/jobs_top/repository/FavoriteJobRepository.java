package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.FavoriteJobView;
import com.example.jobs_top.model.FavoriteJob;
import com.example.jobs_top.model.pk.FavoriteJobId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteJobRepository extends JpaRepository<FavoriteJob, Long> {
    @Query("""
        SELECT f.id AS id, j.id AS jobId, j.title AS title, j.requirements AS requirements, j.status AS status,
               j.salaryMin AS salaryMin, j.salaryMax AS salaryMax,
               r.companyName AS companyName, j.city AS city, 
               j.jobType AS jobType, j.experienceLevel AS experienceLevel
        FROM FavoriteJob f
        JOIN f.job j
        JOIN f.user u
        JOIN j.recruiterProfile r
        WHERE u.id = :userId 
    """)
    List<FavoriteJobView> findFavoriteJobByUserId(Long userId);
    boolean existsByUserIdAndJobId(Long userId,Long jobId);


}