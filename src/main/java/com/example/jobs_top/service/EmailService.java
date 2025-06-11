package com.example.jobs_top.service;


import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Candidate;
import com.example.jobs_top.model.Job;
import com.example.jobs_top.repository.CandidateRepository;
import com.example.jobs_top.repository.JobRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;

    public EmailService(JavaMailSender mailSender, JobRepository jobRepository, CandidateRepository candidateRepository) {
        this.mailSender = mailSender;
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
    }

    public void sendApprovedJob(Account account,String jobTitle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jobtopsystem@gmail.com");
        message.setTo(account.getEmail());
        message.setSubject("Thông báo "+jobTitle);
        message.setText("Đơn ứng tuyển "+jobTitle+" của bạn đã được chấp nhận.");
        mailSender.send(message);

    }


    @Transactional
    public void sendJobEmail(List<Long> jobIds,Long candidateId) {

        Candidate candidate=candidateRepository.findById(candidateId).orElseThrow(()->new RuntimeException("Không tìm thấy ứng viên này"));
        List<Job> jobs = jobRepository.findAllById(jobIds);
        String content= Utils.buildJobListEmailContent(jobs);
        sendSimpleMail(candidate.getAccount().getEmail(),"Thông báo việc làm từ JobTop",content);

    }

    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("jobtopsystem@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
