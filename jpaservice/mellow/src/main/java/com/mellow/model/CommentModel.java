package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class CommentModel extends AbstractModel {

    private String content;

    @ManyToOne
    private PostModel post;

    @ManyToOne
    private UserModel user;

    protected CommentModel() {
    }

    public CommentModel(String content) {
        this.content = content;
    }

    public PostModel getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public CommentModel setContent(String content) {
        this.content = content;
        return this;
    }
}
