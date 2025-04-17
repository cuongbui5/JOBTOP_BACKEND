package com.example.jobs_top.service;

import com.example.jobs_top.dto.req.CreateNotification;
import com.example.jobs_top.dto.res.NotificationDto;
import com.example.jobs_top.dto.res.PaginatedResponse;
import com.example.jobs_top.model.Account;
import com.example.jobs_top.model.Notification;
import com.example.jobs_top.repository.AccountRepository;
import com.example.jobs_top.repository.NotificationRepository;
import com.example.jobs_top.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, AccountRepository accountRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.accountRepository = accountRepository;
        this.messagingTemplate = messagingTemplate;
    }
    public PaginatedResponse<?> getAllNotifications(int page,int size){
        Account account= Utils.getAccount();
        Pageable pageable= PageRequest.of(page-1,size, Sort.by("createdAt").descending());
        Page<Notification> notifications = notificationRepository.findByRecipientId(account.getId(),pageable);
        return new PaginatedResponse<>(
                notifications.stream().map(NotificationDto::new).toList(),
                notifications.getTotalPages(),
                page,
                notifications.getTotalElements()
        );

    }

    public void createNotification(CreateNotification createNotification) {
        Notification notification = new Notification();
        notification.setSender(createNotification.getSender());
        notification.setContent(createNotification.getContent());
        Account account = accountRepository
                .findById(createNotification.getReceiptId())
                .orElseThrow(()->new IllegalArgumentException("Account not found"));
        notification.setRecipient(account);
        notification.setRead(false);
        NotificationDto notificationDto= new NotificationDto(notificationRepository.save(notification));
        messagingTemplate.convertAndSendToUser(account.getEmail(),"/queue/notifications", notificationDto);

    }

    // Xóa thông báo theo ID
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
