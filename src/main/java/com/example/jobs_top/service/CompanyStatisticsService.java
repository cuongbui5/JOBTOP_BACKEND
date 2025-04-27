package com.example.jobs_top.service;

import com.example.jobs_top.dto.res.DailyCount;
import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.view.EmployerDashboardView;
import com.example.jobs_top.model.view.JobStatisticsView;
import com.example.jobs_top.repository.ApplicationRepository;
import com.example.jobs_top.repository.EmployerDashboardViewRepository;
import com.example.jobs_top.repository.JobStatisticsViewRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompanyStatisticsService {
    private final EmployerDashboardViewRepository employerDashboardViewRepository;
    private final JobStatisticsViewRepository jobStatisticsViewRepository;
    private final ApplicationRepository applicationRepository;

    public CompanyStatisticsService(EmployerDashboardViewRepository employerDashboardViewRepository, JobStatisticsViewRepository jobStatisticsViewRepository, ApplicationRepository applicationRepository) {
        this.employerDashboardViewRepository = employerDashboardViewRepository;
        this.jobStatisticsViewRepository = jobStatisticsViewRepository;
        this.applicationRepository = applicationRepository;
    }

    public EmployerDashboardView getEmployerDashboardView() {
        return employerDashboardViewRepository.findByAccountId(Utils.getAccount().getId()).orElse(null);
    }

    public JobStatisticsView getJobStatisticsView(Long jobId) {
        return jobStatisticsViewRepository.findByJobId(jobId).orElse(null);
    }


}
