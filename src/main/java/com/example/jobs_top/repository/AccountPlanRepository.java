package com.example.jobs_top.repository;

import com.example.jobs_top.model.AccountPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountPlanRepository extends JpaRepository<AccountPlan, Long> {
    @EntityGraph(attributePaths = {"plan"})
    List<AccountPlan> findByAccountId(Long accountId);
    Optional<AccountPlan> findByAccountIdAndIsCurrentTrue(Long accountId);

}