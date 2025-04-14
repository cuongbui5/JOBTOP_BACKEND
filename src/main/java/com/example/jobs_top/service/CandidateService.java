package com.example.jobs_top.service;

import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Candidate;
import com.example.jobs_top.repository.CandidateRepository;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final AccountRepository accountRepository;



    public CandidateService(CandidateRepository candidateRepository, AccountRepository accountRepository) {
        this.candidateRepository = candidateRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Candidate saveCandidate(Candidate candidate) {
        Account account=Utils.getAccount();

        if(candidate.getId()==null){
            candidate.setAccount(account);
            return candidateRepository.save(candidate);
        }

        Candidate candidateUpdate = candidateRepository
                .findById(candidate.getId())
                .orElseThrow(()->new RuntimeException("Not found candidate"));
        candidateUpdate.setPhone(candidate.getPhone());
        candidateUpdate.setAddress(candidate.getAddress());
        candidateUpdate.setSkills(candidate.getSkills());
        candidateUpdate.setDescription(candidate.getDescription());
        candidateUpdate.setEducation(candidate.getEducation());
        candidateUpdate.setDateOfBirth(candidate.getDateOfBirth());
        candidateUpdate.setGender(candidate.getGender());
        candidateUpdate.setPublicProfile(candidate.getPublicProfile());
        candidateUpdate.setWorkExperience(candidate.getWorkExperience());
        return candidateRepository.save(candidateUpdate);

    }


    public Candidate getCandidateByAccount() {
        Account account=Utils.getAccount();
        return candidateRepository.findByAccountId(account.getId()).orElseThrow(()->new RuntimeException("Bạn chưa có thông tin profile!"));
    }





    public Candidate getUserProfileByUserId(Long userId) {
        Account user= accountRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Not found user"));
        return candidateRepository.findByAccountId(user.getId()).orElseThrow(()->new RuntimeException("Not found candidate"));

    }
}
