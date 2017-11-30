package com.mellow.application.webservice.model;

import com.mellow.application.jpaservice.entity.Like;

public final class LikeDTO {
    private Long id;
    private Long userId;
    private Long postId;

    public LikeDTO(Like like) {
        this.id = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();
    }

    protected LikeDTO() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }
}
