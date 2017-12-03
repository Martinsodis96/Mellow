package com.mellow.application.webservice.model;

import com.mellow.application.jpaservice.entity.Comment;


public final class CommentDTO {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
    }

    protected CommentDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getUserId() {
        return userId;
    }
}
