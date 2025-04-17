package com.example.jobs_top.service;

import com.example.jobs_top.model.Resume;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.repository.ResumeRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final AccountRepository accountRepository;

    public ResumeService(ResumeRepository resumeRepository, AccountRepository accountRepository) {
        this.resumeRepository = resumeRepository;
        this.accountRepository = accountRepository;
    }



    public List<Resume> getAllResumeByAccount() {
        Account account= Utils.getAccount();
        return resumeRepository.findByAccountId(account.getId());
    }



    public Resume createResume(Resume resume) {
        resume.setAccount(Utils.getAccount());
        return resumeRepository.save(resume);
    }

    public Resume updateResume(Long id,Resume resume) {
        Resume cv=resumeRepository.findById(id).orElseThrow(()->new RuntimeException("resume not found"));
        cv.setName(resume.getName());
        cv.setLink(resume.getLink());
        return resumeRepository.save(cv);
    }

    public void deleteResume(Long id) {

        resumeRepository.deleteById(id);


    }

    public Account setResumeDefault(Long id) {
        Account account=Utils.getAccount();
        account.setResumeDefault(id);
        return accountRepository.save(account);

    }
}
