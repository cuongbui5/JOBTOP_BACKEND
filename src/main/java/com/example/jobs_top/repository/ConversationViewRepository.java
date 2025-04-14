package com.example.jobs_top.repository;

import com.example.jobs_top.model.view.ConversationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationViewRepository extends JpaRepository<ConversationView,Long> {
    List<ConversationView> findByAccountId(Long accountId);
    List<ConversationView> findByCompanyId(Long companyId);
}
