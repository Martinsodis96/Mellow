package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    public PostDao(String content) {
        this.content = content;
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

    public UserDao getUser() {
        return user;
    }

    public PostDao setUser(UserDao user) {
        this.user = user;
        return this;
    }
}
