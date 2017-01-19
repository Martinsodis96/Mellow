package com.mellow.model;

public class User {

    private Long id;
    private String username;
    private String password;

    protected User() {
    }

    public User(UserModel userModel) {
        this.id = userModel.getId();
        this.username = userModel.getUsername();
        this.password = userModel.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
