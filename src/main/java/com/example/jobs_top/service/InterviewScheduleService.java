package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateInterviewSchedule;
import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.model.InterviewSlot;
import com.example.jobs_top.model.RecruiterProfile;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.InterviewScheduleStatus;
import com.example.jobs_top.model.enums.SlotStatus;
import com.example.jobs_top.repository.ApplicationRepository;
import com.example.jobs_top.repository.InterviewScheduleRepository;
import com.example.jobs_top.repository.InterviewSlotRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterviewScheduleService {
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewSlotRepository interviewSlotRepository;
    private final RecruiterProfileService recruiterProfileService;


    public InterviewScheduleService(InterviewScheduleRepository interviewScheduleRepository, ApplicationRepository applicationRepository, InterviewSlotRepository interviewSlotRepository, RecruiterProfileService recruiterProfileService) {
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.applicationRepository = applicationRepository;
        this.interviewSlotRepository = interviewSlotRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    public List<InterviewSchedule> findAllByApplicationId(Long applicationId) {
        //return interviewScheduleRepository.findByApplicationId(applicationId);
        return null;
    }

    @Transactional
    public InterviewSchedule createInterviewSchedule(CreateInterviewSchedule createInterviewSchedule) {
        List<Long> applicationIds = createInterviewSchedule.getApplicationIds();
        if (applicationIds == null || applicationIds.isEmpty()) {
            throw new IllegalArgumentException("Cuộc phỏng vấn phải có ít nhất một ứng viên tham gia.");
        }

        InterviewSchedule interviewSchedule = new InterviewSchedule();
        interviewSchedule.setInterviewNote(createInterviewSchedule.getInterviewNote());
        interviewSchedule.setOfficeAddress(createInterviewSchedule.getOfficeAddress());
        interviewSchedule.setStartTime(createInterviewSchedule.getStartTime());
        interviewSchedule.setEndTime(createInterviewSchedule.getEndTime());
        interviewSchedule.setInterviewDate(createInterviewSchedule.getInterviewDate());
        interviewSchedule.setStatus(InterviewScheduleStatus.SCHEDULED);
        interviewSchedule.setCreatedBy(Utils.getUserFromContext().getId());

        InterviewSchedule savedInterviewSchedule = interviewScheduleRepository.save(interviewSchedule);

        List<Application> applications = applicationRepository.findAllByIdIn(applicationIds);

        /*if (applications.size() != applicationIds.size()) {
            throw new IllegalArgumentException("Một hoặc nhiều ID ứng viên không tồn tại trong hệ thống.");
        }*/

        List<InterviewSlot> interviewSlots = applications.stream().map(application -> {
            application.setStatus(ApplicationStatus.ADDED_TO_INTERVIEW);
            InterviewSlot interviewSlot = new InterviewSlot();
            interviewSlot.setApplication(application);
            interviewSlot.setInterviewSchedule(savedInterviewSchedule);
            return interviewSlot;
        }).toList();


        interviewSlotRepository.saveAll(interviewSlots);
        applicationRepository.saveAll(applications);

        return savedInterviewSchedule;
    }


    public InterviewSchedule updateInterviewSchedule(Long id,CreateInterviewSchedule createInterviewSchedule) {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findById(id).orElseThrow(()->new RuntimeException("InterviewSchedule not found"));
        interviewSchedule.setInterviewNote(createInterviewSchedule.getInterviewNote());
        interviewSchedule.setOfficeAddress(createInterviewSchedule.getOfficeAddress());
        interviewSchedule.setStartTime(createInterviewSchedule.getStartTime());
        interviewSchedule.setEndTime(createInterviewSchedule.getEndTime());
        if(interviewSchedule.getStatus() != InterviewScheduleStatus.SCHEDULED){
            throw new RuntimeException("Hành động không được chấp nhận");
        }

        if(createInterviewSchedule.getStatus()==InterviewScheduleStatus.COMPLETED){
            LocalDateTime interviewDateTime = interviewSchedule.getInterviewDate()
                    .atTime(interviewSchedule.getEndTime());
            LocalDateTime currentDateTime = LocalDateTime.now();
            if (!currentDateTime.isAfter(interviewDateTime)) {
                throw new RuntimeException("Chỉ có thể cập nhật hoàn thành sau khi phỏng vấn đã kết thúc ");

            }
        }

        interviewSchedule.setStatus(createInterviewSchedule.getStatus());
        return interviewScheduleRepository.save(interviewSchedule);
    }

    public InterviewSchedule cancelInterviewSchedule(Long id) {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findById(id).orElseThrow(()->new RuntimeException("InterviewSchedule not found"));
        interviewSchedule.setStatus(InterviewScheduleStatus.CANCELED_BY_RECRUITER);
        return interviewScheduleRepository.save(interviewSchedule);
    }

    public List<InterviewSchedule> getAllInterviewSchedules() {
        return interviewScheduleRepository.findByCreatedBy(Utils.getUserFromContext().getId());
    }

    public InterviewSchedule getInterviewById(Long id) {
        return interviewScheduleRepository.findById(id).orElseThrow(()->new RuntimeException("InterviewSchedule not found"));
    }
}
