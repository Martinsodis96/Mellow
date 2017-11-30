package com.mellow.application.webservice.model;

import com.mellow.application.jpaservice.entity.User;

public final class UserDTO {
    private Long id;
    private String username;

    protected UserDTO() {
    }

    public UserDTO(User userModel) {
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
