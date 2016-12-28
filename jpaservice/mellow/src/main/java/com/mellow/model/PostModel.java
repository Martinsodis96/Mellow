package com.mellow.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PostModel extends AbstractModel {

    private String content;

    @OneToMany(mappedBy = "post")
    private Set<CommentModel> comments;

    @OneToMany(mappedBy = "post")
    private Set<LikeModel> likes;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserModel user;

    protected PostModel() {
    }

    public PostModel(String content, UserModel user) {
        this.content = content;
        this.user = user;
        this.likes = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public String getContent() {
        return content;
    }

    public Set<CommentModel> getComments() {
        return comments;
    }

    public PostModel setComments(Set<CommentModel> comments) {
        this.comments = comments;
        return this;
    }

    public Set<LikeModel> getLikes() {
        return likes;
    }

    public PostModel setLikes(Set<LikeModel> likes) {
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
