package com.example.jobs_top.repository;

import com.example.jobs_top.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.price) FROM Transaction t")
    BigDecimal getTotalRevenue();

    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.createdAt >= :start AND t.createdAt < :end")
    BigDecimal getRevenueInRange(ZonedDateTime start, ZonedDateTime end);

    /*@Query("SELECT t.plan.name, SUM(t.price) FROM Transaction t GROUP BY t.plan.name")
    List<Object[]> getRevenueByPlan();*/
}
