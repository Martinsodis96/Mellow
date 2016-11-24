package com.mellow.model;

/**
 * Created by Martin on 2016-11-24.
 */
public class Post {

    private String contentText;
    private String Firstname;
    private String Lastname;


    public Post(String contentText, String firstname, String lastname) {
        this.contentText = contentText;
        Firstname = firstname;
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}
