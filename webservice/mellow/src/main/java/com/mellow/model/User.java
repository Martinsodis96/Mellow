package com.mellow.model;

public class User {

    private String username;

    protected User() {
    }

    public User(UserModel userModel) {
        this.username = userModel.getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
