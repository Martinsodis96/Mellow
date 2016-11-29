package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Like extends AbstractEntity {

    @ManyToOne
    private Post post;

    protected Like() {
    }

    public Post getPost() {
        return post;
    }
}
