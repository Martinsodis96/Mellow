package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`like`")
public class LikeModel extends AbstractModel {

    @ManyToOne
    private PostModel post;

    @ManyToOne
    private UserModel user;

    protected LikeModel() {
    }

    public LikeModel(PostModel post, UserModel user) {
        this.post = post;
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public PostModel getPost() {
        return post;
    }
}
