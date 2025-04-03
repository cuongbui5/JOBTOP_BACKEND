package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.SlotUserView;
import com.example.jobs_top.dto.view.SlotView;
import com.example.jobs_top.model.InterviewSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewSlotRepository extends JpaRepository<InterviewSlot, Long> {
    @Query("""
        SELECT s.id AS id,
        s.status AS status,
        s.updatedAt AS updatedAt,
        s.application.id AS applicationId,
        s.application.job.title AS jobTitle,
        s.application.job.id AS jobId,
        s.interviewSchedule.status AS interviewScheduleStatus,
        s.interviewSchedule.id AS interviewScheduleId,
        s.interviewSchedule.officeAddress AS officeAddress,
        s.interviewSchedule.interviewNote AS interviewNote,
        s.interviewSchedule.interviewDate AS interviewDate,
        s.interviewSchedule.startTime AS startTime,
        s.interviewSchedule.endTime AS endTime
        FROM InterviewSlot s
 
        WHERE s.application.userId=:userId
    """)
    List<SlotUserView> findByApplicationUserId(Long userId);
    @Query("""
        SELECT s.id AS id,
        s.status AS status,
        s.application.resume as resume,
        s.application.resume.user.userProfile.fullName as fullName,
        s.application.resume.user.userProfile.phone as phone,
        s.application.resume.user.userProfile.image as image,
        s.application.job.title as jobTitle,
        s.application.job.id as jobId
        FROM InterviewSlot s
        WHERE s.interviewSchedule.id=:interviewScheduleId
    """)
    List<SlotView> findByInterviewId(Long interviewScheduleId);

    List<InterviewSlot> findByInterviewScheduleId(Long interviewScheduleId);
}