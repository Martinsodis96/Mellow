package com.mellow.model;

public class User {

    private Long id;
    private String username;

    protected User() {
    }

    public User(UserModel userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
