package com.example.jobs_top.dto.res;

import com.example.jobs_top.model.Conversation;
import com.example.jobs_top.model.enums.RoleType;

public class ConversationDto {
    private Long id;
    private String name;
    private String image;
    private MessageDto message;

    public ConversationDto(Conversation conversation, RoleType roleType) {
        this.id = conversation.getId();
        if(roleType==RoleType.EMPLOYER){
            this.name=conversation.getAccount().getEmail();
            this.image=conversation.getAccount().getAvatar();
        }else {
            this.name=conversation.getCompany().getName();
            this.image=conversation.getCompany().getLogo();

        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }
}
