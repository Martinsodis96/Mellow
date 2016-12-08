package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
public class Post extends AbstractEntity{

    private String content;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private Set<Like> likes;

    @ManyToOne
    private User user;

    protected Post() {
    }

    public Post(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
