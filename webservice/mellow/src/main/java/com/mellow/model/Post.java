package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Post extends AbstractEntity{

    private String content;
    private Long likes;

    @ManyToOne
    private User user;

    protected Post() {
    }

    public Post(String content) {
        this.content = content;
    }
}
