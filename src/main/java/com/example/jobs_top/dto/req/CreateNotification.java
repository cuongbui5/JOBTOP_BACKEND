package com.example.jobs_top.dto.req;

public class CreateNotification {
    private Long receiptId;
    private String content;
    private String sender;

    public CreateNotification(Long receiptId, String content, String sender) {
        this.receiptId = receiptId;
        this.content = content;
        this.sender = sender;
    }

    public CreateNotification() {
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
