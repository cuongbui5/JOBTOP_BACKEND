package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateReport;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.model.Report;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.repository.ReportRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final JobRepository jobRepository;

    public ReportService(ReportRepository reportRepository, JobRepository jobRepository) {
        this.reportRepository = reportRepository;
        this.jobRepository = jobRepository;
    }

    public Report createReport(CreateReport createReport) {
        Job job=jobRepository.findById(createReport.getJobId()).orElseThrow(()->new RuntimeException("Not found job"));
        Report report = new Report();
        report.setUser(Utils.getUserFromContext());
        report.setJob(job);
        report.setReason(createReport.getReason());
        report.setAdditionalInfo(createReport.getAdditionalInfo());
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return  reportRepository.findAll();
        
        
    }
}
