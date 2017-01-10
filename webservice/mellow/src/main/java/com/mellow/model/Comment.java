package com.mellow.model;

public class Comment {

    private Long id;
    private String content;
    private Long postId;
    private User user;

    public Comment(CommentModel commentModel) {
        this.id = commentModel.getId();
        this.content = commentModel.getContent();
        this.postId = commentModel.getPost().getId();
        this.user = new User(commentModel.getUser());
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

    public User getUser() {
        return user;
    }
}
