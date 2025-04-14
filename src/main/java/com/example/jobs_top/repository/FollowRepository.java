package com.example.jobs_top.repository;

import com.example.jobs_top.model.Follow;
import com.example.jobs_top.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByAccountIdAndCompanyId(Long accountId, Long companyId);
    Optional<Follow> findByAccountIdAndCompanyId(Long accountId, Long companyId);

    @Query("SELECT r FROM Follow f JOIN f.company r JOIN FETCH r.category WHERE f.account.id = :accountId")
    List<Company> findFollowedCompaniesByAccountId(@Param("accountId") Long accountId);

}