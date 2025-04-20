package com.example.jobs_top.repository;

import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    @Query("SELECT a FROM Account a WHERE (:role IS NULL OR a.role = :role)")
    Page<Account> findByRoleOptional(@Param("role") RoleType role, Pageable pageable);

    @Query("""
    SELECT a, c FROM Account a
    LEFT JOIN Company c ON c.account.id = a.id
    WHERE (:role IS NULL OR a.role = :role)
      AND (:companyName IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :companyName, '%')))
""")
    Page<Object[]> findAccountsWithCompanyAndFilter(
            @Param("role") RoleType role,
            @Param("companyName") String companyName,
            Pageable pageable
    );


}
