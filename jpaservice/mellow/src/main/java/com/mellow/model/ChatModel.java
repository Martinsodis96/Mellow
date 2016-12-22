package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ChatModel extends AbstractModel {

    @OneToMany(mappedBy = "chat")
    private List<MessageModel> messages;

    @ManyToMany
    private List<UserModel> users;

    public ChatModel(List<UserModel> users) {
        this.users = users;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public ChatModel setMessages(List<MessageModel> messages) {
        this.messages = messages;
        return this;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public ChatModel addUser(UserModel user) {
        this.users.add(user);
        return this;
    }
    public ChatModel removeUser(UserModel user) {
        this.users.remove(user);
        return this;
    }

}
