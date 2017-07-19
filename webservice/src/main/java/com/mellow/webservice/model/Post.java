package com.mellow.webservice.model;

import com.mellow.application.jpaservice.entity.model.PostModel;

import java.util.ArrayList;
import java.util.List;

public final class Post {
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
        if (post.getUser() != null) {
            this.user = new User(post.getUser());
        }
        if (post.getLikes() != null) {
            likes = new ArrayList<>();
            post.getLikes().forEach(likeModel -> this.likes.add(new Like(likeModel)));
        }
        if (post.getComments() != null) {
            comments = new ArrayList<>();
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

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}