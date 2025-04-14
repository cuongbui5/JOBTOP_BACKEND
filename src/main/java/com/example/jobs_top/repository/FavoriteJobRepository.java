package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.FavoriteJobView;
import com.example.jobs_top.model.FavoriteJob;
import com.example.jobs_top.model.pk.FavoriteJobId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteJobRepository extends JpaRepository<FavoriteJob, Long> {
    @Query("""
        SELECT f.id AS id, j.id AS jobId, j.title AS title, j.requirements AS requirements, j.status AS status,
               j.salaryMin AS salaryMin, j.salaryMax AS salaryMax,
               j.company.name AS companyName, j.city AS city,
               j.views AS views,
               j.jobType AS jobType, j.experienceLevel AS experienceLevel
        FROM FavoriteJob f
        JOIN f.job j
        JOIN f.account a
        WHERE a.id = :accountId
    """)
    List<FavoriteJobView> findFavoriteJobByAccountId(@Param("accountId") Long accountId);
    boolean existsByAccountIdAndJobId(Long accountId,Long jobId);


}