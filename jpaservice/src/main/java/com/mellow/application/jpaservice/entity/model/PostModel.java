package com.mellow.application.jpaservice.entity.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`post`")
public class PostModel extends AbstractModel {

    private String content;

    @OneToMany(mappedBy = "post")
    private List<CommentModel> comments;

    @OneToMany(mappedBy = "post")
    private List<LikeModel> likes;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserModel user;

    protected PostModel() {
    }

    public PostModel(String content, UserModel user) {
        this.content = content;
        this.user = user;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public PostModel setComments(List<CommentModel> comments) {
        this.comments = comments;
        return this;
    }

    public List<LikeModel> getLikes() {
        return likes;
    }

    public PostModel setLikes(List<LikeModel> likes) {
        this.likes = likes;
        return this;
    }

    public PostModel addLike(LikeModel like) {
        this.likes.add(like);
        return this;
    }

    public PostModel removeLike(LikeModel like) {
        this.likes.remove(like);
        return this;
    }

    public UserModel getUser() {
        return user;
    }

    public PostModel setUser(UserModel user) {
        this.user = user;
        return this;
    }

    public PostModel setContent(String content) {
        this.content = content;
        return this;
    }
}
