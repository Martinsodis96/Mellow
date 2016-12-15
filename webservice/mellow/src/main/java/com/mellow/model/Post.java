package com.mellow.model;

public class Post {

    private Long id;
    private String contentText;

    public Post(PostDao post) {
        this.id = post.getId();
        this.contentText = post.getContent();
    }

    public Long getId() {
        return id;
    }

    public String getContentText() {
        return contentText;
    }
}