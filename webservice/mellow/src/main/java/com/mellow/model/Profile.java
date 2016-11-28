package com.mellow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Profile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    public Profile() {
    }

    public Profile(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Profile setUsername(String username) {
        this.username = username;
        return this;
    }

    public Profile setId(Long id) {
        this.id = id;
        return this;
    }
}