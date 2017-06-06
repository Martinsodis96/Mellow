package com.mellow.webservice.model;


public final class User {
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

    public Long getId() {
        return id;
    }
}
