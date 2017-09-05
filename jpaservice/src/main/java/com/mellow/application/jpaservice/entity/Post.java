package com.mellow.application.jpaservice.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`post`")
public class Post extends AbstractEntity {

    private String content;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    protected Post() {
    }

    public Post(String content, User user) {
        this.content = content;
        this.user = user;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Post setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public Post setLikes(List<Like> likes) {
        this.likes = likes;
        return this;
    }

    public Post addLike(Like like) {
        this.likes.add(like);
        return this;
    }

    public Post removeLike(Like like) {
        this.likes.remove(like);
        return this;
    }

    public User getUser() {
        return user;
    }

    public Post setUser(User user) {
        this.user = user;
        return this;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }
}
