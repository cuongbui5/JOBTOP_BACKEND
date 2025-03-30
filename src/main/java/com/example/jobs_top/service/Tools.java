package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.ApplicationDetailsRequest;
import com.example.jobs_top.dto.req.ApplyJobRequest;
import com.example.jobs_top.model.Application;
import com.example.jobs_top.model.enums.ApplicationStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.NestedExceptionUtils;

import java.util.function.Function;

@Configuration
public class Tools {
    private final ApplicationService applicationService;

    public Tools(ApplicationService applicationService) {
        this.applicationService = applicationService;
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
}
