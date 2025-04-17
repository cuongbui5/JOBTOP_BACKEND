package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateMessage;
import com.example.jobs_top.dto.res.ConversationDto;
import com.example.jobs_top.dto.res.MessageDto;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.model.Conversation;
import com.example.jobs_top.model.Message;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.enums.RoleType;
import com.example.jobs_top.repository.ConversationRepository;
import com.example.jobs_top.repository.MessageRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(ConversationRepository conversationRepository, MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }
    @Transactional
    public PaginatedResponse<?> getAllMessages(Long conversationId, int page, int size) {
        Account account=Utils.getAccount();
        List<Message> messagesNotRead=new ArrayList<>();
        Pageable pageable = PageRequest.of(page-1, size, Sort.by("createdAt").descending());
        Page<Message> messagePage = messageRepository.findByConversationId(conversationId, pageable);
        List<MessageDto> messageDtos = messagePage.getContent().stream()
                .map((message -> {
                    if(message.getSender()!=account&& !message.isRead()){
                        message.setRead(true);
                        messagesNotRead.add(message);
                    }

                    return new MessageDto(message);
                }))
                .collect(Collectors.toList());

        messageRepository.saveAll(messagesNotRead);



        Collections.reverse(messageDtos);
        return new PaginatedResponse<>(
                messageDtos,
                messagePage.getTotalPages(),
                page,
                messagePage.getTotalElements()

        );
    }




    @Transactional
    public MessageDto createMessage(CreateMessage createMessage) {
        Conversation conversation = conversationRepository
                .findById(createMessage.getConversationId())
                .orElseThrow(()->new RuntimeException("conversation not found"));

        Account account= Utils.getAccount();
        Message message = new Message();
        message.setConversation(conversation);
        message.setContent(createMessage.getContent());
        message.setRead(false);
        message.setSender(account);
        Long receiverId;
        if(account.getRole()== RoleType.EMPLOYER){
            receiverId=conversation.getAccount().getId();
        }else {
            receiverId=conversation.getCompany().getAccount().getId();
        }

        MessageDto messageDto= new MessageDto(messageRepository.save(message));
        messagingTemplate.convertAndSend("/topic/inbox/"+receiverId, messageDto);
        return messageDto;
    }
}
