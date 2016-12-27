package com.mellow.model;

import java.util.List;

public class Chat {

    private List<User> users;

    private List<Message> messages;

    public Chat(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Chat setMessages(List<Message> messages) {
        this.messages = messages;
        return this;
    }

    public List<User> getUsers() {
        return users;
    }

    public Chat setUsers(List<User> users) {
        this.users = users;
        return this;
    }
}
