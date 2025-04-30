package com.example.jobs_top.repository;

import com.example.jobs_top.dto.view.JobCardView;
import com.example.jobs_top.model.Candidate;
import com.example.jobs_top.model.enums.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
  Optional<Candidate> findByAccountId(Long accountId);


  @Query("SELECT DISTINCT c.desiredPosition FROM Candidate c WHERE c.desiredPosition IS NOT NULL")
  List<String> findAllDistinctDesiredPositions();
  @Query("SELECT DISTINCT c.city FROM Candidate c WHERE c.city IS NOT NULL")
  List<String> findAllDistinctCity();

  @Query("SELECT c FROM Candidate c " +
          "WHERE (:keyword IS NULL OR " +
          "LOWER(c.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
          "OR LOWER(c.desiredPosition) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
          "AND (:experienceLevel IS NULL OR c.workExperience = :experienceLevel) " +
          "AND (:educationLevel IS NULL OR c.education = :educationLevel) " +
          "AND (:gender IS NULL OR c.gender = :gender) " +
          "AND (:positionLevel IS NULL OR c.positionLevel = :positionLevel) " +
          "AND (:industry IS NULL OR c.desiredPosition = :industry) " +
          "AND (:city IS NULL OR c.city = :city)")
  Page<Candidate> findAllCandidateWithFilters(
          @Param("keyword") String keyword,
          @Param("experienceLevel") ExperienceLevel experienceLevel,
          @Param("educationLevel") EducationLevel educationLevel,
          @Param("gender") Gender gender,
          @Param("positionLevel") PositionLevel positionLevel,
          @Param("industry") String industry,
          @Param("city") String city,
          Pageable pageable
  );

}