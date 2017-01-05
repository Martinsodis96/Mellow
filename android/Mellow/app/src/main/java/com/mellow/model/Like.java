package com.mellow.model;

public class Like {

    private Long id;
    private Long userId;
    private Long postId;

    public Like(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public Like setPostId(Long postId) {
        this.postId = postId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public Like setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getId() {
        return id;
    }
}
