package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends AbstractEntity {

    @ManyToOne
    private Post post;
    private String content;

    public Comment(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
