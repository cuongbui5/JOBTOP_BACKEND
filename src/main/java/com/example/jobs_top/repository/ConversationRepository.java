package com.example.jobs_top.repository;


import com.example.jobs_top.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
   List<Conversation> findByAccountId(Long accountId);
   List<Conversation> findByCompanyId(Long companyId);
   Optional<Conversation> findByAccountIdAndCompanyId(Long accountId, Long companyId);





}