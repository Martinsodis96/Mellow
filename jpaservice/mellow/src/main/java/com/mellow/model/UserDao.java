package com.mellow.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserDao extends AbstractEntity{

    @Column(unique = true, nullable = false)
    private String username;
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "user")
    private List<CommentDao> comments;

    @OneToMany(mappedBy = "user")
    private List<LikeDao> likes;

    @OneToMany(mappedBy = "user")
    private List<MessageDao> messages;

    @OneToMany(mappedBy = "user")
    private List<PostDao> posts;

    @ManyToMany
    private List<ChatDao> chats;

    protected UserDao() {
    }

    public UserDao(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public UserDao setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<CommentDao> getComments() {
        return comments;
    }

    public UserDao setComments(List<CommentDao> comments) {
        this.comments = comments;
        return this;
    }

    public List<LikeDao> getLikes() {
        return likes;
    }

    public UserDao setLikes(List<LikeDao> likes) {
        this.likes = likes;
        return this;
    }

    public List<MessageDao> getMessages() {
        return messages;
    }

    public UserDao setMessages(List<MessageDao> messages) {
        this.messages = messages;
        return this;
    }

    public List<PostDao> getPosts() {
        return posts;
    }

    public UserDao setPosts(List<PostDao> posts) {
        this.posts = posts;
        return this;
    }

    public List<ChatDao> getChats() {
        return chats;
    }

    public UserDao setChats(List<ChatDao> chats) {
        this.chats = chats;
        return this;
    }
}