package com.mellow.application.jpaservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`comment`")
public class Comment extends AbstractEntity {

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Comment() {
    }

    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public Comment setId(Long id) {
        super.id = id;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Comment setPost(Post post) {
        this.post = post;
        return this;
    }

    public Comment setUser(User user) {
        this.user = user;
        return this;
    }
}
