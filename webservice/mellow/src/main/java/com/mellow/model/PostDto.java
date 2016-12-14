package com.mellow.model;

public class PostDto {

    private Long id;
    private String contentText;

    public PostDto(Post post) {
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