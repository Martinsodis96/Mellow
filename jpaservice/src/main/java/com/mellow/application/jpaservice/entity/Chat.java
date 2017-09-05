package com.mellow.application.jpaservice.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "`chat`")
public class Chat extends AbstractEntity {

    @ManyToMany
    private List<User> users;

    public Chat(List<User> users) {
        this.users = users;
    }

    protected Chat() {
    }

    public List<User> getUsers() {
        return users;
    }

    public Chat addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Chat removeUser(User user) {
        this.users.remove(user);
        return this;
    }

}
