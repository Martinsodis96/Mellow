package com.mellow.model;

import java.util.List;

public class Post {

    private Long id;
    private String content;
    private User user;
    private List<Like> likes;
    private List<Comment> comments;

    protected Post() {
    }

    public Post(PostModel post) {
        this.id = post.getId();
        this.content = post.getContent();
        if (post.getUser() != null){
            this.user = new User(post.getUser());
        }
        if (post.getLikes() != null){
            post.getLikes().forEach(likeModel -> this.likes.add(new Like(likeModel)));
        }
        if (post.getComments() != null){
            post.getComments().forEach(commentModel -> this.comments.add(new Comment(commentModel)));
        }
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }
}