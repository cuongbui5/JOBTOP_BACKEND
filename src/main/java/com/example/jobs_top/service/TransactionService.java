package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateAccountPlan;
import com.example.jobs_top.dto.res.MonthlyRevenueDto;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.AccountPlan;
import com.example.jobs_top.model.Plan;
import com.example.jobs_top.model.Transaction;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.repository.PlanRepository;
import com.example.jobs_top.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final PlanRepository planRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, PlanRepository planRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.planRepository = planRepository;
    }

    @Transactional
    public void saveTransaction(Long planId, Long accountId) {
        Account account= accountRepository.getReferenceById(accountId);
        Plan plan= planRepository.getReferenceById(planId);
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setPlan(plan);
        transaction.setPrice(plan.getPrice());
        transactionRepository.save(transaction);
    }

    public BigDecimal getTotalRevenue() {
        return transactionRepository.getTotalRevenue();
    }

    public BigDecimal getRevenueByDate(LocalDate date) {
        ZonedDateTime start = date.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = start.plusDays(1);
        return transactionRepository.getRevenueInRange(start, end);
    }

    public BigDecimal getRevenueByMonth(int month, int year) {
        ZonedDateTime start = LocalDate.of(year, month, 1).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = start.plusMonths(1);
        return transactionRepository.getRevenueInRange(start, end);
    }

    public BigDecimal getRevenueByYear(int year) {
        ZonedDateTime start = LocalDate.of(year, 1, 1).atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime end = start.plusYears(1);
        return transactionRepository.getRevenueInRange(start, end);
    }

    public List<MonthlyRevenueDto> getMonthlyRevenueOfYear(int year) {
        List<MonthlyRevenueDto> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            BigDecimal revenue = getRevenueByMonth(month, year);
            result.add(new MonthlyRevenueDto(month, revenue));
        }

        return result;
    }



}
