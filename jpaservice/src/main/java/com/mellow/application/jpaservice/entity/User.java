package com.mellow.application.jpaservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "`user`")
public class User extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    private String salt;
    private int saltingIterations;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;


    public User() {
    }

    public User(String username, String password, String salt, int saltingIterations) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.saltingIterations = saltingIterations;
    }

    public User setId(Long id) {
        super.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSaltingIterations() {
        return saltingIterations;
    }

    public void setSaltingIterations(int saltingIterations) {
        this.saltingIterations = saltingIterations;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public User setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public User setLikes(List<Like> likes) {
        this.likes = likes;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User setPosts(List<Post> posts) {
        this.posts = posts;
        return this;
    }
}