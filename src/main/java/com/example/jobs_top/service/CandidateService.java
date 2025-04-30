package com.example.jobs_top.service;

import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Candidate;
import com.example.jobs_top.model.enums.EducationLevel;
import com.example.jobs_top.model.enums.ExperienceLevel;
import com.example.jobs_top.model.enums.Gender;
import com.example.jobs_top.model.enums.PositionLevel;
import com.example.jobs_top.repository.CandidateRepository;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
        candidateUpdate.setExpectedSalary(candidate.getExpectedSalary());
        candidateUpdate.setCity(candidate.getCity());
        candidateUpdate.setFullName(candidate.getFullName());
        candidateUpdate.setPositionLevel(candidate.getPositionLevel());
        candidateUpdate.setDescription(candidate.getDescription());
        candidateUpdate.setEducation(candidate.getEducation());
        candidateUpdate.setDateOfBirth(candidate.getDateOfBirth());
        candidateUpdate.setGender(candidate.getGender());
        candidateUpdate.setSearchable(candidate.getSearchable());
        candidateUpdate.setDesiredPosition(candidate.getDesiredPosition());
        candidateUpdate.setWorkExperience(candidate.getWorkExperience());
        return candidateRepository.save(candidateUpdate);

    }


    public Candidate getCandidateByAccount() {
        Account account=Utils.getAccount();
        return candidateRepository.findByAccountId(account.getId()).orElseThrow(()->new RuntimeException("Bạn chưa có thông tin profile!"));
    }

    public List<String> getAllUniqueDesiredPositions() {
        return candidateRepository.findAllDistinctDesiredPositions();
    }

    public List<String> getAllUniqueCities() {
        return candidateRepository.findAllDistinctCity();
    }

    public PaginatedResponse<?> findCandidates(int page,
                                               int size,
                                               String keyword,
                                               String city,
                                               String industry,
                                               PositionLevel positionLevel,
                                               ExperienceLevel experienceLevel,
                                               EducationLevel educationLevel,
                                               Gender gender){
        Pageable pageable = PageRequest.of(page - 1, size);


        Page<Candidate> candidatePage=candidateRepository
                .findAllCandidateWithFilters(
                        keyword,
                        experienceLevel,
                        educationLevel,
                        gender,
                        positionLevel,
                        industry,
                        city,
                        pageable
                );

        return new PaginatedResponse<>(
                candidatePage.getContent(),
                candidatePage.getTotalPages(),
                page,
                candidatePage.getTotalElements()
        );


    }






    public Candidate getUserProfileByUserId(Long userId) {
        Account user= accountRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Not found user"));
        return candidateRepository.findByAccountId(user.getId()).orElseThrow(()->new RuntimeException("Not found candidate"));

    }
}
