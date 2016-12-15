package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CommentDao extends AbstractEntity {

    @ManyToOne
    private PostDao post;
    private String content;

    @ManyToOne
    private UserDao user;

    protected CommentDao() {
    }

    public CommentDao(String content) {
        this.content = content;
    }

    public PostDao getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public CommentDao setContent(String content) {
        this.content = content;
        return this;
    }
}
