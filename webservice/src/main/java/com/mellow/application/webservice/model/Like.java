package com.mellow.application.webservice.model;

import com.mellow.application.jpaservice.entity.model.LikeModel;

public final class Like {
    private Long id;
    private Long userId;
    private Long postId;

    public Like(LikeModel likeModel) {
        this.id = likeModel.getId();
        this.userId = likeModel.getUser().getId();
        this.postId = likeModel.getPost().getId();
    }

    protected Like() {
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
