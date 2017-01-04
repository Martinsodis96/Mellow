package com.mellow.model;

public class Comment {

    private String content;
    private PostModel post;
    private UserModel user;

    public Comment(CommentModel commentModel) {
        this.content = commentModel.getContent();
        if(commentModel.getUser() != null){
            this.user = commentModel.getUser();
        }
        if(commentModel.getPost() != null){
            this.post = commentModel.getPost();
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
