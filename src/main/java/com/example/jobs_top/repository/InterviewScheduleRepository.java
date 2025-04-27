package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.InterviewStatusCountView;
import com.example.jobs_top.model.InterviewSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {
    List<InterviewSchedule> findByCreatedBy(Long userId);

    @Query("SELECT a.interviewSchedule FROM Application a WHERE a.account.id = :accountId AND a.interviewSchedule IS NOT NULL")
    Page<InterviewSchedule> findInterviewSchedulesByAccountId(@Param("accountId") Long accountId, Pageable pageable);
    @Query("SELECT i.status AS status, COUNT(i) AS count FROM InterviewSchedule i GROUP BY i.status")
    List<InterviewStatusCountView> countInterviewsByStatus();

}