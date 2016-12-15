package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ChatDao extends AbstractEntity{

    @OneToMany(mappedBy = "chat")
    private List<MessageDao> messages;

    @ManyToMany
    private List<UserDao> users;

    public ChatDao() {
    }

    public List<MessageDao> getMessages() {
        return messages;
    }

    public ChatDao setMessages(List<MessageDao> messages) {
        this.messages = messages;
        return this;
    }

    public List<UserDao> getUsers() {
        return users;
    }

    public ChatDao setUsers(List<UserDao> users) {
        this.users = users;
        return this;
    }
}
