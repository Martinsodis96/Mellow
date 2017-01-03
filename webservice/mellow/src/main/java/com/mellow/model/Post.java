package com.mellow.model;

public class Post {

    private Long id;
    private String contentText;
    private UserModel userModel;

    protected Post() {
    }

    public Post(PostModel post) {
        this.id = post.getId();
        this.contentText = post.getContent();
        if (post.getUser() != null){
            this.userModel = post.getUser();
        }
    }

    public Long getId() {
        return id;
    }

    public String getContentText() {
        return contentText;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}