package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`like`")
public class LikeDao extends AbstractEntity {

    @ManyToOne
    private PostDao post;

    @ManyToOne
    private UserDao user;

    protected LikeDao() {
    }

    public UserDao getUser() {
        return user;
    }

    public PostDao getPost() {
        return post;
    }
}
