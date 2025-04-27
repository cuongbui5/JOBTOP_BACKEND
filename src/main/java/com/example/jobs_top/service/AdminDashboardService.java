package com.example.jobs_top.service;

import com.example.jobs_top.dto.view.*;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdminDashboardService {

    private final AccountRepository accountRepository;
    private final JobRepository jobRepository;
    private final InterviewScheduleRepository interviewScheduleRepository;
    private final ApplicationRepository applicationRepository;
    private final PlanRepository planRepository;

    public AdminDashboardService(AccountRepository accountRepository,
                                 JobRepository jobRepository,
                                 InterviewScheduleRepository interviewScheduleRepository,
                                 ApplicationRepository applicationRepository,
                                 PlanRepository planRepository) {
        this.accountRepository = accountRepository;
        this.jobRepository = jobRepository;
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.applicationRepository = applicationRepository;
        this.planRepository = planRepository;
    }

    public List<RoleCountView> getAccountCountsByRole() {
        return accountRepository.countAccountsByRole();
    }


    public List<JobStatusCountView> getJobCountByStatus() {
        return jobRepository.countJobsByStatus();
    }

    public List<InterviewStatusCountView> getInterviewCountsByStatus() {
        return interviewScheduleRepository.countInterviewsByStatus();
    }
    public List<ApplicationStatusCountView> countApplicationByStatus() {
        return applicationRepository.countApplicationsByStatus();
    }

    public List<Job> getTopNViewedJobs(int n) {
        return jobRepository.findTopViewedJobs(PageRequest.of(0, n));
    }
    public List<PlanUsageCountView> getTopUsedPlans() {
        return planRepository.countPlanUsage();
    }
}
