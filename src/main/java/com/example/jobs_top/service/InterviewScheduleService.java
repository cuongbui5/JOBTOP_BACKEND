package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateInterviewSchedule;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.InterviewSchedule;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.InterviewStatus;
import com.example.jobs_top.repository.ApplicationRepository;
import com.example.jobs_top.repository.InterviewScheduleRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterviewScheduleService {
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewScheduleService(InterviewScheduleRepository interviewScheduleRepository, ApplicationRepository applicationRepository) {
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.applicationRepository = applicationRepository;
    }


    @Transactional
    public InterviewSchedule createInterviewSchedule(CreateInterviewSchedule createInterviewSchedule) {
        if (createInterviewSchedule.getInterviewDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày phỏng vấn phải lớn hơn ngày hiện tại");
        }
        if (createInterviewSchedule.getStartTime().isAfter(createInterviewSchedule.getEndTime()) ||
                createInterviewSchedule.getStartTime().equals(createInterviewSchedule.getEndTime())) {
            throw new IllegalArgumentException("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc");
        }
        InterviewSchedule interviewSchedule = new InterviewSchedule();
        interviewSchedule.setInterviewNote(createInterviewSchedule.getInterviewNote());
        interviewSchedule.setOfficeAddress(createInterviewSchedule.getOfficeAddress());
        interviewSchedule.setStartTime(createInterviewSchedule.getStartTime());
        interviewSchedule.setEndTime(createInterviewSchedule.getEndTime());
        interviewSchedule.setInterviewDate(createInterviewSchedule.getInterviewDate());
        interviewSchedule.setStatus(InterviewStatus.SCHEDULED);
        interviewSchedule.setCreatedBy(Utils.getAccount().getId());
        return interviewScheduleRepository.save(interviewSchedule);
    }

    @Transactional
    public InterviewSchedule updateInterviewSchedule(Long id,CreateInterviewSchedule createInterviewSchedule) {
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findById(id).orElseThrow(()->new RuntimeException("InterviewSchedule not found"));
        if(interviewSchedule.getStatus() != InterviewStatus.SCHEDULED){
            //chỉ được cập nhật khi đã lên lịch
            throw new IllegalArgumentException("Hành động không được chấp nhận");
        }
        if (createInterviewSchedule.getInterviewDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Không thể cập nhật lịch phỏng vấn với ngày đã qua");
        }
        if (createInterviewSchedule.getStartTime().isAfter(createInterviewSchedule.getEndTime()) ||
                createInterviewSchedule.getStartTime().equals(createInterviewSchedule.getEndTime())) {
            throw new IllegalArgumentException("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc");
        }
        interviewSchedule.setInterviewNote(createInterviewSchedule.getInterviewNote());
        interviewSchedule.setOfficeAddress(createInterviewSchedule.getOfficeAddress());
        interviewSchedule.setStartTime(createInterviewSchedule.getStartTime());
        interviewSchedule.setEndTime(createInterviewSchedule.getEndTime());
        interviewSchedule.setInterviewDate(createInterviewSchedule.getInterviewDate());

        if(createInterviewSchedule.getStatus() == InterviewStatus.COMPLETED){
            LocalDateTime interviewDateTime = interviewSchedule.getInterviewDate()
                    .atTime(interviewSchedule.getEndTime());
            LocalDateTime currentDateTime = LocalDateTime.now();
            if (!currentDateTime.isAfter(interviewDateTime)) {
                throw new IllegalArgumentException("Phỏng vấn chưa kết thúc");

            }
            List<Application> applications=applicationRepository.findByInterviewScheduleId(id);
            applications.forEach(a-> a.setStatus(ApplicationStatus.COMPLETED));
            applicationRepository.saveAll(applications);

        }

        interviewSchedule.setStatus(createInterviewSchedule.getStatus());
        return interviewScheduleRepository.save(interviewSchedule);
    }







    public List<InterviewSchedule> getAllInterviewSchedules() {
        return interviewScheduleRepository.findByCreatedBy(Utils.getAccount().getId());
    }

    public InterviewSchedule findInterviewById(Long id) {
        return interviewScheduleRepository.findById(id).orElseThrow(()->new RuntimeException("InterviewSchedule not found"));
    }

    public PaginatedResponse<?> getAllInterviewSchedulesByAccount(int page,int size) {
        Account account = Utils.getAccount();
        Pageable pageable = PageRequest.of(page-1,size,Sort.by("createdAt").descending());
        Page<InterviewSchedule> interviewSchedulePage = interviewScheduleRepository
                .findInterviewSchedulesByAccountId(account.getId(), pageable);
        return new PaginatedResponse<>(
                interviewSchedulePage.getContent(),
                interviewSchedulePage.getTotalPages(),
                page,
                interviewSchedulePage.getTotalElements()
        );
    }
}
