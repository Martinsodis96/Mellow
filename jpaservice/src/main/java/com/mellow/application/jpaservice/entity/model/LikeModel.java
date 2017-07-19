package com.mellow.application.jpaservice.entity.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`like`")
public class LikeModel extends AbstractModel {

    @ManyToOne(fetch = FetchType.EAGER)
    private PostModel post;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserModel user;

    protected LikeModel() {
    }

    public LikeModel(PostModel post, UserModel user) {
        this.post = post;
        this.user = user;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
