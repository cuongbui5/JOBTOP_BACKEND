package com.example.jobs_top.service;


import com.example.jobs_top.dto.res.InterviewScheduleDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Supplier;


@Configuration(proxyBeanMethods = false)
public class CandidateTools {
    private final ApplicationService applicationService;

    public CandidateTools(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Bean
    @Description("Tra cứu lịch phỏng vấn")
    Supplier<List<InterviewScheduleDto>> getInterviewSchedules() {
        return () -> {
            try {
                return applicationService.getInterviewScheduleByAccount();
            }
            catch (Exception e) {

                return List.of();
            }
        };
    }





}

 /*@Bean
    @Description("Get application details")
    public Function<ApplicationDetailsRequest, Application> getApplicationDetails() {
        return request -> {
            try {

                return applicationService.getApplicationDetails(request.getUsername(), request.getJobName(),request.getCompanyName());
            }
            catch (Exception e) {
                //logger.warn("Booking details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return new Application( null, null, null);
            }
        };
    }

    @Bean
    @Description("Withdraw a job application")
    public Function<ApplicationDetailsRequest, Application> withdrawJobApplication() {
        return request -> {
            try {
                return applicationService.withdrawApplication(request.getUsername(), request.getJobName(),request.getCompanyName());
            }
            catch (Exception e) {
                //logger.warn("Booking details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return null;
            }
        };
    }

    @Bean
    @Description("Apply for job application")
    public Function<ApplyJobRequest, Application> applyJobApplication() {
        return request -> {
            try {
                return applicationService.applyForJob(request.getUsername(), request.getJobName(),request.getCompanyName());
            }
            catch (Exception e) {
                //logger.warn("Booking details: {}", NestedExceptionUtils.getMostSpecificCause(e).getMessage());
                return null;
            }
        };
    }*/
