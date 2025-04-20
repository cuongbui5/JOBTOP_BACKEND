package com.example.jobs_top.repository;

import com.example.jobs_top.model.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
  Optional<Company> findByAccountId(Long accountId);
  Optional<Company> findByName(String name);
  @EntityGraph(attributePaths = {"category"})
  List<Company> findAll();







}