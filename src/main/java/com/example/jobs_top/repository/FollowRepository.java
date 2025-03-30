package com.example.jobs_top.repository;

import com.example.jobs_top.model.Follow;
import com.example.jobs_top.model.RecruiterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByUserIdAndRecruiterId(Long userId, Long recruiterId);
    Optional<Follow> findByUserIdAndRecruiterId(Long userId, Long recruiterId);

    @Query("SELECT r FROM Follow f JOIN f.recruiter r JOIN FETCH r.category WHERE f.user.id = :userId")
    List<RecruiterProfile> findFollowedCompaniesByUserId(@Param("userId") Long userId);

}