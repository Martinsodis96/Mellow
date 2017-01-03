package com.mellow.model;

import java.util.List;

public class Post {

    private String contentText;
    private User user;
    private List<Like> likes;

    public Post(String contentText, User user) {
        this.contentText = contentText;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public Post setLikes(List<Like> likes) {
        this.likes = likes;
        return this;
    }
}
