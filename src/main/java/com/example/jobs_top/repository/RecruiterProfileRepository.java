package com.example.jobs_top.repository;

import com.example.jobs_top.dto.res.RecruiterJobCountDto;
import com.example.jobs_top.dto.view.RecruiterProfileView;
import com.example.jobs_top.model.RecruiterProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {

  @EntityGraph(attributePaths = {"category"})
  <T> Optional<T> findByUserId(Long userId, Class<T> type);

  @Query("SELECT rp.id FROM RecruiterProfile rp WHERE rp.user.id = :userId")
  Long getRecruiterProfileIdByUserId(Long userId);

  @EntityGraph(attributePaths = {"category"})
  List<RecruiterProfile> findAll();

  @EntityGraph(attributePaths = {"category"})
  Optional<RecruiterProfile> findById(Long id);


}