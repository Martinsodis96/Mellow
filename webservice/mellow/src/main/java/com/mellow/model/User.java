package com.mellow.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    @Column(unique = true, nullable = false)
    private String username;
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @ManyToMany
    private List<Chat> chats;

    protected User() {
    }

    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}