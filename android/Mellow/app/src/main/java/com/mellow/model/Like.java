package com.mellow.model;

public class Like {

    private Long PostId;
    private Long userId;

    public Like(Long postId, Long userId) {
        PostId = postId;
        this.userId = userId;
    }

    public Long getPostId() {
        return PostId;
    }

    public void setPostId(Long postId) {
        PostId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
