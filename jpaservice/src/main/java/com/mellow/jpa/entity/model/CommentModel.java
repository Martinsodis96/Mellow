package com.mellow.jpa.entity.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`comment`")
public class CommentModel extends AbstractModel {

    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private PostModel post;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserModel user;

    protected CommentModel() {
    }

    public CommentModel(String content, PostModel post) {
        this.content = content;
        this.post = post;
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

    public UserModel getUser() {
        return user;
    }

    public CommentModel setPost(PostModel post) {
        this.post = post;
        return this;
    }

    public CommentModel setUser(UserModel user) {
        this.user = user;
        return this;
    }
}
