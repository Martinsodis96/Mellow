package com.mellow.entity.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`user`")
public class UserModel extends AbstractModel {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    private String salt;
    private int saltingIterations;

    @OneToMany(mappedBy = "user")
    private List<CommentModel> comments;

    @OneToMany(mappedBy = "user")
    private List<LikeModel> likes;

    @OneToMany(mappedBy = "user")
    private List<MessageModel> messages;

    @OneToMany(mappedBy = "user")
    private List<PostModel> posts;

    @ManyToMany
    private List<ChatModel> chats;

    protected UserModel() {
    }

    public UserModel(String username, String password, String salt, int saltingIterations) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.saltingIterations = saltingIterations;
    }

    public String getUsername() {
        return username;
    }

    public UserModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSaltingIterations() {
        return saltingIterations;
    }

    public void setSaltingIterations(int saltingIterations) {
        this.saltingIterations = saltingIterations;
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