package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class MessageDao extends AbstractEntity{

    private String message;

    @ManyToOne
    private UserDao user;

    @ManyToOne
    private ChatDao chatDao;

    protected MessageDao() {
    }

    public MessageDao(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ChatDao getChatDao() {
        return chatDao;
    }
}
