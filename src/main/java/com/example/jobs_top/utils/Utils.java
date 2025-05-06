package com.example.jobs_top.utils;


import com.example.jobs_top.dto.res.JobDto;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Utils {
    public static Account getAccount() {
        UserPrincipal userPrincipal  = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }
    public static String buildJobDtoContent(JobDto job) {
        StringBuilder content = new StringBuilder();

        content.append("🔹 Vị trí: ").append(job.getTitle()).append("\n");
        content.append("🏢 Công ty: ").append(job.getCompany().getName()).append("\n");
        content.append("📍 Địa điểm: ").append(job.getCity()).append(", ").append(job.getCity()).append("\n");
        content.append("💼 Hình thức làm việc: ").append(job.getJobType().getLabel()).append("\n");
        content.append("📈 Kinh nghiệm: ").append(job.getExperienceLevel().getLabel()).append("\n");

        content.append("💰 Mức lương: ");
        if (job.getSalaryMin() != null && job.getSalaryMax() != null) {
            content.append(String.format("%,d - %,d VND", job.getSalaryMin(), job.getSalaryMax()));
        } else {
            content.append("Thỏa thuận");
        }
        content.append("\n");

        content.append("📅 Hạn nộp hồ sơ: ").append(job.getApplicationDeadline()).append("\n");
        content.append("📎 Mô tả: ")
                .append(job.getDescription() != null && job.getDescription().length() > 100
                        ? job.getDescription().substring(0, 100) + "..."
                        : job.getDescription()).append("\n");
        content.append("👉 Ứng tuyển ngay!\n\n");

        return content.toString();
    }

    public static String buildJobDtoListEmailContent(List<JobDto> jobs) {
        StringBuilder content = new StringBuilder();

        content.append("Chào bạn,\n\n");
        content.append("Dưới đây là một số cơ hội việc làm phù hợp dành cho bạn:\n\n");

        for (JobDto job : jobs) {
            content.append(buildJobDtoContent(job));
        }

        content.append("Chúc bạn sớm tìm được công việc phù hợp!\n");
        content.append("Trân trọng,\n");
        content.append("Đội ngũ JobsTop");

        return content.toString();
    }


    public static String buildJobItemContent(Job job) {
        StringBuilder content = new StringBuilder();

        content.append("🔹 Vị trí: ").append(job.getTitle()).append("\n");
        content.append("🏢 Công ty: ").append(job.getCompany().getName()).append("\n");
        content.append("📍 Địa điểm: ").append(job.getCity()).append(", ").append(job.getCity()).append("\n");
        content.append("💼 Hình thức làm việc: ").append(job.getJobType().getLabel()).append("\n");
        content.append("📈 Kinh nghiệm: ").append(job.getExperienceLevel().getLabel()).append("\n");

        content.append("💰 Mức lương: ");
        if (job.getSalaryMin() != null && job.getSalaryMax() != null) {
            content.append(String.format("%,d - %,d VND", job.getSalaryMin(), job.getSalaryMax()));
        } else {
            content.append("Thỏa thuận");
        }
        content.append("\n");

        content.append("📅 Hạn nộp hồ sơ: ").append(job.getApplicationDeadline()).append("\n");
        content.append("📎 Mô tả: ")
                .append(job.getDescription() != null && job.getDescription().length() > 100
                        ? job.getDescription().substring(0, 100) + "..."
                        : job.getDescription()).append("\n");
        content.append("👉 Ứng tuyển ngay!\n\n");

        return content.toString();
    }



    public static String buildJobListEmailContent(List<Job> jobs) {
        StringBuilder content = new StringBuilder();

        content.append("Chào bạn,\n\n");
        content.append("Dưới đây là một số cơ hội việc làm phù hợp dành cho bạn:\n\n");

        for (Job job : jobs) {
            content.append(buildJobItemContent(job));
        }

        content.append("Chúc bạn sớm tìm được công việc phù hợp!\n");
        content.append("Trân trọng,\n");
        content.append("Đội ngũ JobsTop");

        return content.toString();
    }


    public static Date zonedDateTimeToDate(String zonedDateTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(zonedDateTimeStr, formatter);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
}
