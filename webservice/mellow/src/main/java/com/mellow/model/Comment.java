package com.mellow.model;

public class Comment {

    private Long id;
    private String content;
    private Long postId;
    private Long userId;

    public Comment(CommentModel commentModel) {
        this.id = commentModel.getId();
        this.content = commentModel.getContent();
        this.postId = commentModel.getPost().getId();
        this.userId = commentModel.getUser().getId();
    }

    protected Comment() {
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
