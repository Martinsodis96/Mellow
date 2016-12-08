package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Message extends AbstractEntity{

    private String message;

    @ManyToOne
    private Chat chat;

    protected Message() {
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Chat getChat() {
        return chat;
    }
}
