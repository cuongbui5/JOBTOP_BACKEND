package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.ReviewView;
import com.example.jobs_top.model.InterviewReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InterviewReviewRepository extends JpaRepository<InterviewReview, Long> {
    InterviewReview findByInterviewSlotId(Long interviewSlotId);
    @Query("""
        SELECT r.id AS id,
        r.updatedAt AS updatedAt,
        r.comment AS comment,
        r.rating as rating,
        r.reviewer.email as email,
        r.interviewSlot.interviewSchedule.interviewDate as interviewDate
        FROM InterviewReview r
        WHERE r.jobId=:jobId
    """)
    Page<ReviewView> findByJobId(Long jobId, Pageable pageable);

    @Query(value = """
        WITH ratings AS (
            SELECT 1 AS rating UNION ALL
            SELECT 2 UNION ALL
            SELECT 3 UNION ALL
            SELECT 4 UNION ALL
            SELECT 5
        )
        SELECT r.rating, COALESCE(ir.total_reviews, 0) AS total_reviews
        FROM ratings r
        LEFT JOIN (
            SELECT rating, COUNT(*) AS total_reviews
            FROM _interview_review
            WHERE job_id = :jobId
            GROUP BY rating
        ) ir ON r.rating = ir.rating
        ORDER BY r.rating DESC
    """, nativeQuery = true)
    List<Object[]> getRatingStatisticsByJobId(@Param("jobId") Long jobId);





}