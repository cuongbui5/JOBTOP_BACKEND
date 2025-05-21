package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.AddToInterviewRequest;
import com.example.jobs_top.dto.req.ApplyRequest;
import com.example.jobs_top.dto.req.CreateNotification;
import com.example.jobs_top.dto.res.ApplicationStatisticsDTO;
import com.example.jobs_top.dto.res.ApplicationStatusStatsDTO;
import com.example.jobs_top.dto.res.InterviewScheduleDto;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.dto.view.ApplicationRecruiterView;
import com.example.jobs_top.dto.view.ApplicationUserView;
import com.example.jobs_top.exception.NotFoundException;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.InterviewStatus;
import com.example.jobs_top.repository.ApplicationRepository;
import com.example.jobs_top.repository.InterviewScheduleRepository;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.repository.ResumeRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final CompanyService companyService;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final NotificationService notificationService;

    public ApplicationService(ApplicationRepository applicationRepository, ResumeRepository resumeRepository, JobRepository jobRepository, CompanyService companyService, InterviewScheduleRepository interviewScheduleRepository, NotificationService notificationService) {
        this.applicationRepository = applicationRepository;
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
        this.companyService = companyService;
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.notificationService = notificationService;
    }


    public Application applyJobAI(String jobName,String companyName){
        Account account=Utils.getAccount();
        if(!validateResume(account)){
            throw new IllegalArgumentException("Chưa cập nhật thông tin CV");
        }



        Optional<Job> job=jobRepository.findByTitleAndCompany(jobName,companyName);
        if(job.isEmpty()){
            throw new RuntimeException("Not found job");
        }

        if(!validateApplication(job.get().getId(),account.getId())){
            throw new IllegalArgumentException("Bạn đã ứng tuyển công việc này. Nếu muốn ứng tuyển lại vui lòng quay lại sau 7 ngày!");
        }

        Resume resume=resumeRepository.getReferenceById(account.getResumeDefault());
        Application application=new Application();
        application.setJob(job.get());
        application.setResume(resume);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAccount(account);
        String content = String.format("Bạn đã ứng tuyển vào vị trí %s", job.get().getTitle());
        notificationService.createNotification(
                new CreateNotification(account.getId(),
                        content,
                        "Hệ thống"));
        return applicationRepository.save(application);


    }


    public boolean validateApplication(Long jobId,Long accountId){
        Optional<Application> applicationOptional=applicationRepository
                .findByJobIdAndAccountIdOrderByCreatedAtDesc(jobId,accountId);

        if(applicationOptional.isPresent()){
            Application application=applicationOptional.get();
            long daysBetween = ChronoUnit.DAYS.between(application.getCreatedAt(), ZonedDateTime.now());
            return daysBetween >= 7;
        }
        return true;
    }

    public boolean validateResume(Account account){
        if(account.getResumeDefault()==null){
           return false;
        }
        return true;
    }




    @Transactional
    public Application applyJob(Long jobId) {
        Account account=Utils.getAccount();
        if(account.getResumeDefault()==null){
            throw new IllegalArgumentException("Chưa cập nhật thông tin CV");
        }

        Optional<Application> applicationOptional=applicationRepository
                .findByJobIdAndAccountIdOrderByCreatedAtDesc(jobId, account.getId());

        if(applicationOptional.isPresent()){
            Application application=applicationOptional.get();
            long daysBetween = ChronoUnit.DAYS.between(application.getCreatedAt(), ZonedDateTime.now());
            if (daysBetween<7){
                long remainingDays = 7 - daysBetween;
                throw new IllegalArgumentException("Bạn cần đợi thêm " + remainingDays + " ngày trước khi có thể ứng tuyển lại. Cảm ơn bạn đã quan tâm!");
            }
        }
        Job job=jobRepository.getReferenceById(jobId);
        Resume resume=resumeRepository.getReferenceById(account.getResumeDefault());
        Application application=new Application();
        application.setJob(job);
        application.setResume(resume);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAccount(account);
        String content = String.format("Bạn đã ứng tuyển vào vị trí %s", job.getTitle());
        notificationService.createNotification(
                new CreateNotification(account.getId(),
                content,
                "Hệ thống"));
        return applicationRepository.save(application);

    }



    public PaginatedResponse<ApplicationUserView> getAppliedJobs(int page,int size, ApplicationStatus status) {
        Account account=Utils.getAccount();
        Pageable pageable=PageRequest.of(page-1,size,Sort.by("updatedAt").descending());
        Page<ApplicationUserView> applicationUserViewPage= applicationRepository.findAppliedJobsByAccount(account.getId(),status,pageable);
        return new PaginatedResponse<>(
                applicationUserViewPage.getContent(),
                applicationUserViewPage.getTotalPages(),
                page,
                applicationUserViewPage.getTotalElements()
        );
    }



    public Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(()->new RuntimeException("Application not found"));
    }

    @Transactional
    public Application viewApplication(Long id) {
        Application application=getApplication(id);
        ApplicationStatus currentStatus=application.getStatus();
        ApplicationStatus newStatus=ApplicationStatus.VIEWED;
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Trạng thái không hợp lệ: không thể chuyển từ " + currentStatus.getLabel() + " sang " + newStatus.getLabel());
        }
        application.setStatus(ApplicationStatus.VIEWED);
        Account account=application.getAccount();
        String content = String
                .format("Nhà tuyển dụng %s vừa xem hồ sơ ứng tuyển của bạn",
                        application.getJob().getCompany().getName());
        notificationService.createNotification(
                new CreateNotification(account.getId(),
                        content,
                        "Hệ thống"));
        return applicationRepository.save(application);


    }


    @Transactional
    public Application rejectApplication(Long id) {
        Application application=getApplication(id);
        ApplicationStatus currentStatus=application.getStatus();
        ApplicationStatus newStatus=ApplicationStatus.REJECTED;
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Trạng thái không hợp lệ: không thể chuyển từ " + currentStatus.getLabel() + " sang " + newStatus.getLabel());
        }
        application.setStatus(ApplicationStatus.REJECTED);
        Account account=application.getAccount();
        String content = String
                .format("Nhà tuyển dụng %s đã từ chối hồ sơ ứng tuyển của bạn",
                        application.getJob().getCompany().getName());
        notificationService.createNotification(
                new CreateNotification(account.getId(),
                        content,
                        "Hệ thống"));
        return applicationRepository.save(application);

    }
    @Transactional
    public Application approveApplication(Long id) {
        Application application=applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
        ApplicationStatus currentStatus=application.getStatus();
        ApplicationStatus newStatus=ApplicationStatus.APPROVED;
        if (!currentStatus.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Trạng thái không hợp lệ: không thể chuyển từ " + currentStatus.getLabel() + " sang " + newStatus.getLabel());
        }
        application.setStatus(ApplicationStatus.APPROVED);
        Account account=application.getAccount();
        String content = String
                .format("Nhà tuyển dụng %s đã chấp nhận hồ sơ ứng tuyển của bạn",
                        application.getJob().getCompany().getName());
        notificationService.createNotification(
                new CreateNotification(account.getId(),
                        content,
                        "Hệ thống"));
        return applicationRepository.save(application);

    }

    public PaginatedResponse<ApplicationRecruiterView> getAllApplicationsByFilter(int page,int size,Long scheduleId,Long jobId,ApplicationStatus status) {
        Pageable pageable=PageRequest.of(page-1,size, Sort.by("createdAt").descending());
        if(scheduleId!=null){
            Page<ApplicationRecruiterView> viewPage= applicationRepository.findByScheduleId(scheduleId,pageable);
            return new PaginatedResponse<>(
                    viewPage.getContent(),
                    viewPage.getTotalPages(),
                    page,
                    viewPage.getTotalElements()
            );
        }

        Account account=Utils.getAccount();

        Company company= companyService.getCompanyByAccount(account);
        Page<ApplicationRecruiterView> viewPage= applicationRepository.findApplicationByFilter(company.getId(),jobId,status,pageable);
        return new PaginatedResponse<>(
          viewPage.getContent(),
          viewPage.getTotalPages(),
          page,
          viewPage.getTotalElements()
        );


    }

    @Transactional
    public void addToInterview(Long interviewId, AddToInterviewRequest addToInterviewRequest) {
        InterviewSchedule interviewSchedule=interviewScheduleRepository.findById(interviewId)
                .orElseThrow(()->new NotFoundException("Not found interview schedule"));

        if(interviewSchedule.getStatus()==InterviewStatus.SCHEDULED){
            List<Application> applications = getApplicationsInIds(addToInterviewRequest);

            applications.forEach(a->{
                if (a.getInterviewSchedule() != null) {
                    throw new IllegalArgumentException("Ứng viên đã được gán vào lịch phỏng vấn.");

                }
                ApplicationStatus currentStatus=a.getStatus();
                ApplicationStatus newStatus=ApplicationStatus.ADDED_TO_INTERVIEW;
                if (!currentStatus.canTransitionTo(newStatus)) {
                    throw new IllegalStateException("Trạng thái không hợp lệ: không thể chuyển từ " + currentStatus.getLabel() + " sang " + newStatus.getLabel());
                }
                a.setStatus(ApplicationStatus.ADDED_TO_INTERVIEW);
                a.setInterviewSchedule(interviewSchedule);
                Account account=a.getAccount();
                String content = String
                        .format("Nhà tuyển dụng %s đã tạo lịch phỏng vấn cho bạn",
                                a.getJob().getCompany().getName());
                notificationService.createNotification(
                        new CreateNotification(account.getId(),
                                content,
                                "Hệ thống"));
            });

            applicationRepository.saveAll(applications);
        }else {
            throw new IllegalArgumentException("Buổi phỏng vấn này đã kết thúc hoặc bị hủy");
        }





    }



    @Transactional
    public void removeFromInterview(AddToInterviewRequest removeRequest) {
        List<Application> applications= getApplicationsInIds(removeRequest);
        applications.forEach(a -> {
            a.setInterviewSchedule(null);
            ApplicationStatus currentStatus=a.getStatus();
            ApplicationStatus newStatus=ApplicationStatus.REJECTED;
            if (!currentStatus.canTransitionTo(newStatus)) {
                throw new IllegalStateException("Trạng thái không hợp lệ: không thể chuyển từ " + currentStatus.getLabel() + " sang " + newStatus.getLabel());
            }
            a.setStatus(ApplicationStatus.REJECTED);
            Account account=a.getAccount();
            String content = String
                    .format("Nhà tuyển dụng %s đã xóa bạn khỏi cuộc phỏng vấn",
                            a.getJob().getCompany().getName());
            notificationService.createNotification(
                    new CreateNotification(account.getId(),
                            content,
                            "Hệ thống"));
        });

        applicationRepository.saveAll(applications);
    }

    private List<Application> getApplicationsInIds(AddToInterviewRequest removeRequest) {
        List<Long> applicationIds = removeRequest.getApplicationIds();
        List<Application> applications = applicationRepository.findAllByIdIn(applicationIds);

        if (applications.isEmpty()) {
            throw new IllegalArgumentException("Not found any application");
        }

        if (applications.size() != applicationIds.size()) {
            throw new IllegalArgumentException("Some applications not found for provided IDs");
        }
        return applications;
    }


    public void markNoShowApplicants(AddToInterviewRequest addToInterviewRequest) {
        List<Application> applications= getApplicationsInIds(addToInterviewRequest);
        applications.forEach(a -> {
            ApplicationStatus currentStatus=a.getStatus();
            ApplicationStatus newStatus=ApplicationStatus.NO_SHOW;
            if (!currentStatus.canTransitionTo(newStatus)) {
                throw new IllegalStateException("Trạng thái không hợp lệ: không thể chuyển từ " + currentStatus.getLabel() + " sang " + newStatus.getLabel());
            }

            a.setStatus(ApplicationStatus.NO_SHOW);
        });

        applicationRepository.saveAll(applications);
    }

    public ApplicationStatisticsDTO getApplicationStatistics(Long jobId) {
        List<Object[]> result = applicationRepository.countApplicationsByStatusWithTotal(jobId);

        Map<String, Long> resultMap = new HashMap<>();
        long total = 0;

        for (Object[] row : result) {
            String status = (String) row[0];
            long count = ((Number) row[1]).longValue();

            if ("TOTAL".equals(status)) {
                total = count;
            } else {
                resultMap.put(status, count);
            }
        }

        List<ApplicationStatusStatsDTO> stats = new ArrayList<>();
        for (ApplicationStatus statusEnum : ApplicationStatus.values()) {
            String statusStr = statusEnum.name();
            stats.add(new ApplicationStatusStatsDTO(
                    statusStr,
                    resultMap.getOrDefault(statusStr, 0L)
            ));
        }

        return new ApplicationStatisticsDTO(jobId, total, stats);
    }
    @Transactional
    public List<InterviewScheduleDto> getInterviewScheduleByAccount() {
        Account account=Utils.getAccount();

        List<InterviewScheduleDto> interviewScheduleDtos= applicationRepository
                .findInterviewSchedulesByAccountIdAndStatus(account.getId(), InterviewStatus.SCHEDULED)
                .stream()
                .map(InterviewScheduleDto::new).toList();

        System.out.println(interviewScheduleDtos.size());
        return interviewScheduleDtos;
    }


}
