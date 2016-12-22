package com.mellow.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserModel extends AbstractModel {

    @Column(unique = true, nullable = false)
    private String username;
    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "user")
    private List<CommentModel> comments;

    @OneToMany(mappedBy = "user")
    private List<LikeModel> likes;

    @OneToMany(mappedBy = "user")
    private List<MessageModel> messages;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<PostModel> posts;

    @ManyToMany
    private List<ChatModel> chats;

    protected UserModel() {
    }

    public UserModel(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public UserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public UserModel setComments(List<CommentModel> comments) {
        this.comments = comments;
        return this;
    }

    public List<LikeModel> getLikes() {
        return likes;
    }

    public UserModel setLikes(List<LikeModel> likes) {
        this.likes = likes;
        return this;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public UserModel setMessages(List<MessageModel> messages) {
        this.messages = messages;
        return this;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public UserModel setPosts(List<PostModel> posts) {
        this.posts = posts;
        return this;
    }

    public List<ChatModel> getChats() {
        return chats;
    }

    public UserModel setChats(List<ChatModel> chats) {
        this.chats = chats;
        return this;
    }
}