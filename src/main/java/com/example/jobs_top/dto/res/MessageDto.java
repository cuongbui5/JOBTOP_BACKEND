package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Message;
import com.example.jobs_top.model.enums.RoleType;

import java.time.ZonedDateTime;

public class MessageDto {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String senderName;
    private String content;
    private boolean isRead ;
    private ZonedDateTime createdAt;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.conversationId=message.getConversation().getId();
        this.content = message.getContent();
        this.createdAt = message.getCreatedAt();
        this.senderId=message.getSender().getId();
        this.isRead = message.isRead();
        if(message.getSender().getRole()== RoleType.EMPLOYER){
            this.senderName=message.getConversation().getCompany().getName();
        }else {
            this.senderName=message.getSender().getEmail();
        }



    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }





    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
