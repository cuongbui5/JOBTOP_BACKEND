package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateConversation;
import com.example.jobs_top.dto.res.ConversationDto;
import com.example.jobs_top.model.*;
import com.example.jobs_top.model.enums.RoleType;
import com.example.jobs_top.model.view.ConversationView;
import com.example.jobs_top.repository.*;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final CompanyRepository companyRepository;
    private final ConversationViewRepository conversationViewRepository;

    public ConversationService(ConversationRepository conversationRepository, CompanyRepository companyRepository, ConversationViewRepository conversationViewRepository) {
        this.conversationRepository = conversationRepository;
        this.companyRepository = companyRepository;
        this.conversationViewRepository = conversationViewRepository;
    }


    public List<?> getConversationsForAccount() {
        Account account= Utils.getAccount();
        if(account.getRole()==RoleType.CANDIDATE){
            return conversationViewRepository.findByAccountId(account.getId());


        }else if (account.getRole()==RoleType.EMPLOYER){
            Company company=companyRepository
                    .findByAccountId(account.getId())
                    .orElseThrow(()->new IllegalArgumentException("Not found company"));

            return conversationViewRepository.findByCompanyId(company.getId());


        }

        throw new IllegalArgumentException("Not supported yet.");

    }



    @Transactional
    public Conversation create(CreateConversation createConversation) {
        Account account = Utils.getAccount();

        Company company = companyRepository
                .findByName(createConversation.getCompanyName())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        return conversationRepository
                .findByAccountIdAndCompanyId(account.getId(), company.getId())
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();
                    conversation.setCompany(company);
                    conversation.setAccount(account);
                    return conversationRepository.save(conversation);
                });
    }


    public ConversationView getConversationById(Long id) {
        return conversationViewRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Not found conversation"));
    }
}
