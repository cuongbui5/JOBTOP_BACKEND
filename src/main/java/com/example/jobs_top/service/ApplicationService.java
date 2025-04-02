package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.ApplyRequest;
import com.example.jobs_top.dto.res.JobDetailResponse;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.dto.view.ApplicationRecruiterView;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.ApplicationStatus;
import com.example.jobs_top.model.enums.ViewStatus;
import com.example.jobs_top.repository.ApplicationRepository;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.repository.ResumeRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobService jobService;
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final RecruiterProfileService recruiterProfileService;

    public ApplicationService(ApplicationRepository applicationRepository, JobService jobService, UserProfileService userProfileService, ResumeService resumeService, ResumeRepository resumeRepository, JobRepository jobRepository, RecruiterProfileService recruiterProfileService) {
        this.applicationRepository = applicationRepository;
        this.jobService = jobService;
        this.resumeRepository = resumeRepository;

        this.jobRepository = jobRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    public Application applyForJob(Long jobId) {
        /*JobDetailResponse job = jobService.getJobById(jobId);
        if(job.getApplicationDeadline().isBefore(LocalDate.now())|| job.getApplicationDeadline().isEqual(LocalDate.now())){
            throw new RuntimeException("Thời gian ứng tuyển vị trí này đã kết thúc!");
        }
        User user= Utils.getUserFromContext();
        //UserProfile userProfile=userProfileService.getUserProfileByUserId(user.getId());
        UserProfile userProfile=null;

        if(userProfile.getResume()==null){
            throw new RuntimeException("Bạn chưa thể ứng tuyển được do chưa có đầy đủ thông tin! Vui lòng upload thông tin cv của bạn.");
        }
        Application application = new Application();
        application.setUserProfile(userProfile);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED); // Đặt trạng thái ban đầu là "APPLIED"
        return applicationRepository.save(application);*/
        return null;
    }

    @Transactional
    public Application applyForJob(String username,String jobTitle,String companyName) {

        /*Job job = jobService.findJobByTitleAndCompany(jobTitle,companyName);
        if(job.getApplicationDeadline().isBefore(LocalDate.now())|| job.getApplicationDeadline().isEqual(LocalDate.now())){
            throw new RuntimeException("Thời gian ứng tuyển vị trí này đã kết thúc!");
        }

        //UserProfile userProfile=userProfileService.getUserProfileByUsername(username);
        UserProfile userProfile=new UserProfile();
        if(userProfile.getResume()==null){
            throw new RuntimeException("Bạn chưa thể ứng tuyển được do chưa có đầy đủ thông tin! Vui lòng upload thông tin cv của bạn.");
        }

        Application application = new Application();
        application.setUserProfile(userProfile);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED); // Đặt trạng thái ban đầu là "APPLIED"
        return applicationRepository.save(application);*/
        return null;
    }







    @Transactional
    public Application applyJob(ApplyRequest applyRequest) {
        User user=Utils.getUserFromContext();
        Optional<Application> applicationOptional=applicationRepository
                .findByJobIdAndUserIdOrderByCreatedAtDesc(applyRequest.getJobId(), user.getId());

        if(applicationOptional.isPresent()){
            Application application=applicationOptional.get();
            long daysBetween = ChronoUnit.DAYS.between(application.getCreatedAt(), ZonedDateTime.now());
            if (daysBetween<7){
                long remainingDays = 7 - daysBetween;
                throw new RuntimeException("Bạn cần đợi thêm " + remainingDays + " ngày trước khi có thể ứng tuyển lại. Cảm ơn bạn đã quan tâm!");
            }
        }
        Job job=jobRepository.findById(applyRequest.getJobId()).orElseThrow(()->new RuntimeException("Not found job"));
        Resume resume=resumeRepository.findById(applyRequest.getResumeId()).orElseThrow(()->new RuntimeException("Not found CV"));
        Application application=new Application();
        application.setJob(job);
        application.setResume(resume);
        application.setStatus(ApplicationStatus.PENDING);
        application.setUserId(user.getId());
        return applicationRepository.save(application);

    }

    public List<?> getAppliedJobs() {
        User user=Utils.getUserFromContext();
        return applicationRepository.findAppliedJobsByUser(user.getId());
    }



    public PaginatedResponse<?> getAllApplicationsByRecruiter(int page,int size,String status) {
        RecruiterProfile recruiterProfile=recruiterProfileService.getRecruiterProfileByUser(RecruiterProfile.class);
        Pageable pageable= PageRequest.of(page-1,5);
        ApplicationStatus statusEnum=null;
        if(status!=null){
            statusEnum=ApplicationStatus.valueOf(status);
        }

        Page<ApplicationRecruiterView> recruiterViewPage= applicationRepository
                .findAppliedJobsByRecruiterProfileId(recruiterProfile.getId(),statusEnum,pageable);
        return new PaginatedResponse<>(
                recruiterViewPage.getContent(),
                recruiterViewPage.getTotalPages(),
                page,
                recruiterViewPage.getTotalElements()
        );
    }

    public Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(()->new RuntimeException("Application not found"));
    }

    public Application viewApplication(Long id) {
        Application application=applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
        application.setStatus(ApplicationStatus.VIEWED);
        return applicationRepository.save(application);
    }



    public Application rejectApplication(Long id) {
        Application application=applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    public Application approveApplication(Long id) {
        Application application=applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
        application.setStatus(ApplicationStatus.APPROVED);
        return applicationRepository.save(application);
    }

    public List<ApplicationRecruiterView> getAllApplicationsByFilter( String status, Long jobId) {
        RecruiterProfile recruiterProfile=recruiterProfileService.getRecruiterProfileByUser(RecruiterProfile.class);
        ApplicationStatus statusEnum=null;
        if(status!=null){
            statusEnum=ApplicationStatus.valueOf(status);
        }
        return applicationRepository.findApplicationByFilter(recruiterProfile.getId(),statusEnum,jobId);


    }


    /*@Transactional
    public Application withdrawApplication(String username, String jobName, String companyName) {
        Application application=getApplicationDetails(username,jobName,companyName);
        application.setStatus(ApplicationStatus.WITHDRAWN);
        return applicationRepository.save(application);
    }

    @Transactional
    public Application getApplicationDetails(String username, String jobName, String companyName) {
        return applicationRepository.findLatestApplicationByCriteria(companyName, jobName,username);
    }*/
}
