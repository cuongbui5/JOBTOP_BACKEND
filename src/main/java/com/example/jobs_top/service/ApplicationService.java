package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.AddToInterviewRequest;
import com.example.jobs_top.dto.req.ApplyRequest;
import com.example.jobs_top.dto.res.ApplicationStatisticsDTO;
import com.example.jobs_top.dto.res.ApplicationStatusStatsDTO;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.dto.view.ApplicationRecruiterView;
import com.example.jobs_top.dto.view.ApplicationUserView;
import com.example.jobs_top.exception.NotFoundException;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.ApplicationStatus;
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

    public ApplicationService(ApplicationRepository applicationRepository, ResumeRepository resumeRepository, JobRepository jobRepository, CompanyService companyService, InterviewScheduleRepository interviewScheduleRepository) {
        this.applicationRepository = applicationRepository;
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
        this.companyService = companyService;
        this.interviewScheduleRepository = interviewScheduleRepository;
    }


    @Transactional
    public Application applyJob(ApplyRequest applyRequest) {
        Account account=Utils.getAccount();
        Optional<Application> applicationOptional=applicationRepository
                .findByJobIdAndAccountIdOrderByCreatedAtDesc(applyRequest.getJobId(), account.getId());

        if(applicationOptional.isPresent()){
            Application application=applicationOptional.get();
            long daysBetween = ChronoUnit.DAYS.between(application.getCreatedAt(), ZonedDateTime.now());
            if (daysBetween<7){
                long remainingDays = 7 - daysBetween;
                throw new IllegalArgumentException("Bạn cần đợi thêm " + remainingDays + " ngày trước khi có thể ứng tuyển lại. Cảm ơn bạn đã quan tâm!");
            }
        }
        Job job=jobRepository.findById(applyRequest.getJobId()).orElseThrow(()->new RuntimeException("Not found job"));
        Resume resume=resumeRepository.findById(applyRequest.getResumeId()).orElseThrow(()->new RuntimeException("Not found CV"));
        Application application=new Application();
        application.setJob(job);
        application.setResume(resume);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAccount(account);
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

    public Application viewApplication(Long id) {
        Application application=getApplication(id);
        application.setStatus(ApplicationStatus.VIEWED);
        return applicationRepository.save(application);
    }



    public Application rejectApplication(Long id) {
        Application application=getApplication(id);
        ApplicationStatus status=application.getStatus();
        if(status!=ApplicationStatus.ADDED_TO_INTERVIEW&&status!=ApplicationStatus.COMPLETED){
            application.setStatus(ApplicationStatus.REJECTED);
            return applicationRepository.save(application);

        }
        throw new IllegalArgumentException("Hành động không hợp lệ");

    }

    public Application approveApplication(Long id) {
        Application application=applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
        ApplicationStatus status=application.getStatus();
        if(status!=ApplicationStatus.COMPLETED){
            application.setStatus(ApplicationStatus.APPROVED);
            return applicationRepository.save(application);

        }
        throw new IllegalArgumentException("Hành động không hợp lệ");

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

    public void addToInterview(Long interviewId, AddToInterviewRequest addToInterviewRequest) {
        InterviewSchedule interviewSchedule=interviewScheduleRepository.findById(interviewId)
                .orElseThrow(()->new NotFoundException("Not found interview schedule"));
        List<Application> applications= getApplicationsInIds(addToInterviewRequest);

        applications.forEach(a->{
            a.setStatus(ApplicationStatus.ADDED_TO_INTERVIEW);
            a.setInterviewSchedule(interviewSchedule);
        });

        applicationRepository.saveAll(applications);


    }




    public void removeFromInterview(AddToInterviewRequest removeRequest) {
        List<Application> applications= getApplicationsInIds(removeRequest);
        applications.forEach(app -> {
            app.setInterviewSchedule(null);
            app.setStatus(ApplicationStatus.REJECTED);
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
        applications.forEach(app -> {
            app.setStatus(ApplicationStatus.NO_SHOW);
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


}
