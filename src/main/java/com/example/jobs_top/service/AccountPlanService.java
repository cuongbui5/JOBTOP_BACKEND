package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateAccountPlan;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.AccountPlan;
import com.example.jobs_top.model.Plan;
import com.example.jobs_top.model.enums.Action;
import com.example.jobs_top.repository.AccountPlanRepository;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.repository.PlanRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountPlanService {
    private final AccountPlanRepository accountPlanRepository;
    private final AccountRepository accountRepository;
    private final PlanRepository planRepository;

    public AccountPlanService(AccountPlanRepository accountPlanRepository, AccountRepository accountRepository, PlanRepository planRepository) {
        this.accountPlanRepository = accountPlanRepository;
        this.accountRepository = accountRepository;
        this.planRepository = planRepository;
    }

    public List<AccountPlan> getAllAccountPlan() {
        Account account= Utils.getAccount();
        return accountPlanRepository.findByAccountId(account.getId());
    }

    public AccountPlan findByAccountId(Long accountId) {
        return accountPlanRepository.findByAccountIdAndIsCurrentTrue(accountId)
                .orElseThrow(()->new IllegalArgumentException("Hãy kích hoạt các gói đã mua để sử dụng dịch vụ này."));
    }


    @Transactional
    public void createAccountPlan(CreateAccountPlan createAccountPlan) {


        Account account= accountRepository.getReferenceById(createAccountPlan.getAccountId());
        Plan plan= planRepository.getReferenceById(createAccountPlan.getPlanId());
        AccountPlan accountPlan= new AccountPlan();
        accountPlan.setAccount(account);
        accountPlan.setPlan(plan);
        accountPlan.setCurrent(false);
        accountPlan.setExpiryDate(LocalDateTime.now().plusDays(plan.getDurationDays()));
        accountPlan.setRemainingPosts(plan.getMaxPosts());
        accountPlanRepository.save(accountPlan);
    }
    @Transactional
    public AccountPlan cancelAccountPlan(Long id) {
        AccountPlan accountPlan= accountPlanRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Not found account plan"));
        accountPlan.setCurrent(false);
        return accountPlanRepository.save(accountPlan);
    }

    @Transactional
    public AccountPlan activeAccountPlan(Long id) {
        AccountPlan accountPlan = accountPlanRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Not found account plan with id: " + id));

        if (accountPlan.getRemainingPosts() == 0 || accountPlan.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Gói dịch vụ đã hết lượt đăng hoặc đã hết hạn sử dụng. Vui lòng gia hạn hoặc chọn gói khác.");
        }

        accountPlan.setCurrent(true);
        return accountPlanRepository.save(accountPlan);


    }

    public AccountPlan updateRemainingPosts(Long id, Action action) {
        return accountPlanRepository.findById(id)
                .map(existingAccountPlan -> {
                    if(action==Action.INCREASE){
                        existingAccountPlan.setRemainingPosts(existingAccountPlan.getRemainingPosts()+1);
                    }else {
                        existingAccountPlan.setRemainingPosts(existingAccountPlan.getRemainingPosts()-1);
                    }

                    return accountPlanRepository.save(existingAccountPlan);
                })
                .orElseThrow(() -> new IllegalArgumentException("AccountPlan with id " + id + " not found"));
    }

    public void deleteAccountPlan(Long id) {
        AccountPlan accountPlan=accountPlanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("AccountPlan with id " + id + " not found"));
        //accountPlan.setActive(false);
        accountPlanRepository.save(accountPlan);

    }

}
