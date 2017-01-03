package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`message`")
public class MessageModel extends AbstractModel {

    private String message;

    @ManyToOne
    private UserModel user;

    @ManyToOne
    private ChatModel chat;

    protected MessageModel() {
    }

    public MessageModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ChatModel getChat() {
        return chat;
    }
}
