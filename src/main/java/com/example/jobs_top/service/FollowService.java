package com.example.jobs_top.service;

import com.example.jobs_top.model.Follow;
import com.example.jobs_top.model.Company;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.repository.FollowRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final CompanyService companyService;

    public FollowService(FollowRepository followRepository, CompanyService companyService) {
        this.followRepository = followRepository;
        this.companyService = companyService;
    }

    public Follow getFollowByAccountAndCompany(Long companyId) {
        Account account=Utils.getAccount();
        return followRepository.findByAccountIdAndCompanyId(account.getId(),companyId).orElseThrow(()->new RuntimeException("Not found follow"));
    }



    public Follow followCompany(Long companyId){
        Company company= companyService.getCompanyById(companyId);
        Account account=Utils.getAccount();
        if(followRepository.existsByAccountIdAndCompanyId(account.getId(),companyId)){
            throw new RuntimeException("Bạn đã follow công ty này rồi");
        }
        Follow follow=new Follow();
        follow.setAccount(account);
        follow.setCompany(company);
        return followRepository.save(follow);

    }
    public void unFollowRecruiter(Long followId){
        followRepository.deleteById(followId);

    }

    public List<Company> getFollowedCompaniesByAccount() {
        Account account=Utils.getAccount();
        return followRepository.findFollowedCompaniesByAccountId(account.getId());
    }
}
