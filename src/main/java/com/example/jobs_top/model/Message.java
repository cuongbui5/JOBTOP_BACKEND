package com.example.jobs_top.model;

import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity{
    private String content;
    private boolean isRead;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id",referencedColumnName = "id")
    private Account sender;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;


    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
