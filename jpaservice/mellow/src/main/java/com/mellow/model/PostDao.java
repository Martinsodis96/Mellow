package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PostDao extends AbstractEntity{

    private String content;

    @OneToMany(mappedBy = "post")
    private Set<CommentDao> comments;

    @OneToMany(mappedBy = "post")
    private Set<LikeDao> likes;

    @ManyToOne
    private UserDao user;

    protected PostDao() {
    }

    public PostDao(String content, UserDao user) {
        this.content = content;
        this.user = user;
        this.likes = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public String getContent() {
        return content;
    }

    public Set<CommentDao> getComments() {
        return comments;
    }

    public PostDao setComments(Set<CommentDao> comments) {
        this.comments = comments;
        return this;
    }

    public Set<LikeDao> getLikes() {
        return likes;
    }

    public PostDao setLikes(Set<LikeDao> likes) {
        this.likes = likes;
        return this;
    }

    public PostDao addLike(LikeDao like) {
        this.likes.add(like);
        return this;
    }
    public PostDao removeLike(LikeDao like) {
        this.likes.remove(like);
        return this;
    }


    public UserDao getUser() {
        return user;
    }

    public PostDao setUser(UserDao user) {
        this.user = user;
        return this;
    }

    public PostDao setContent(String content) {
        this.content = content;
        return this;
    }
}
