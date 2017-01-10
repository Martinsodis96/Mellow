package com.mellow.model;

import java.util.List;

public class Post{

    private Long id;
    private String content;
    private User user;
    private List<Like> likes;
    private Comment comment;

    public Post(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public Post setUser(User user) {
        this.user = user;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public Post setLikes(List<Like> likes) {
        this.likes = likes;
        return this;
    }

    public Comment getComment() {
        return comment;
    }

    public Post setComment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Post setId(Long id) {
        this.id = id;
        return this;
    }
}
