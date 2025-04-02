package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateManySlots;
import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.model.InterviewSlot;
import com.example.jobs_top.model.User;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.SlotStatus;
import com.example.jobs_top.repository.ApplicationRepository;
import com.example.jobs_top.repository.InterviewScheduleRepository;
import com.example.jobs_top.repository.InterviewSlotRepository;
import com.example.jobs_top.utils.Utils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@Service
public class InterviewSlotService {
    private final InterviewSlotRepository interviewSlotRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;


    public InterviewSlotService(InterviewSlotRepository interviewSlotRepository, ApplicationRepository applicationRepository, InterviewScheduleRepository interviewScheduleRepository) {
        this.interviewSlotRepository = interviewSlotRepository;
        this.applicationRepository = applicationRepository;
        this.interviewScheduleRepository = interviewScheduleRepository;
    }

    public List<?> getInterviewSlotsByUser() {
        User user= Utils.getUserFromContext();
        return interviewSlotRepository.findByApplicationUserId(user.getId());

    }


    public InterviewSlot updateInterviewSlot(Long id, SlotStatus status) {
        InterviewSlot interviewSlot=interviewSlotRepository.findById(id).orElseThrow(()->new RuntimeException("Not fount slot"));
        interviewSlot.setStatus(status);
        return interviewSlotRepository.save(interviewSlot);
    }


    public List<?>  getAllSlotByInterViewScheduleId(Long id) {
        return interviewSlotRepository.findByInterviewScheduleId(id);
    }

    @Transactional
    public void createSlots(CreateManySlots createManySlots) {
        InterviewSchedule interviewSchedule=interviewScheduleRepository
                .findById(createManySlots.getInterviewScheduleId())
                .orElseThrow(()->new RuntimeException("Not fount interview schedule"));


        List<Application> applications=applicationRepository.findAllByIdIn(createManySlots.getApplicationIds());

        List<InterviewSlot> interviewSlots = applications.stream().map(application -> {
            application.setStatus(ApplicationStatus.ADDED_TO_INTERVIEW);
            InterviewSlot interviewSlot = new InterviewSlot();
            interviewSlot.setApplication(application);
            interviewSlot.setInterviewSchedule(interviewSchedule);
            return interviewSlot;
        }).toList();


        interviewSlotRepository.saveAll(interviewSlots);
        applicationRepository.saveAll(applications);


    }
    @Transactional
    public void deleteSlotById(Long id) {
        InterviewSlot interviewSlot=interviewSlotRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Not fount slot"));
        Application application=interviewSlot.getApplication();
        application.setStatus(ApplicationStatus.APPROVED);


        interviewSlotRepository.deleteById(id);
        applicationRepository.save(application);
    }
}
