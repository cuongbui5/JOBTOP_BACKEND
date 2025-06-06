package com.example.jobs_top.config;

import com.example.jobs_top.dto.res.JobDto;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.service.AiService;
import com.example.jobs_top.service.EmailService;
import com.example.jobs_top.utils.Utils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSchedule {
    private final EmailService emailService;
    private final AccountRepository accountRepository;
    private final AiService aiService;


    public EmailSchedule(EmailService emailService, AccountRepository accountRepository, AiService aiService) {
        this.emailService = emailService;
        this.accountRepository = accountRepository;
        this.aiService = aiService;

    }


    @Scheduled(cron = "0 40 13 * * ?")
    public void sendJobSuggestionsToUsers() {
        List<Account> accounts = accountRepository.findByReceiveEmailTrue();
        accounts.forEach(account -> {
            List<JobDto> jobs=aiService.findTopJobByCandidate(account,1);
            if(jobs!=null&&!jobs.isEmpty()) {
                String content = Utils.buildJobDtoListEmailContent(jobs);
                emailService.sendSimpleMail(account.getEmail(),"Top công việc phù hợp với bạn",content);
            }


        });


    }
}
