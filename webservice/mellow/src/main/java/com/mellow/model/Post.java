package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Post extends AbstractEntity{

    private String content;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    @ManyToOne
    private User user;

    protected Post() {
    }

    public Post(String content) {
        this.content = content;
    }
}
