package com.mellow.application.webservice.model;

import com.mellow.application.jpaservice.entity.Post;

import java.util.ArrayList;
import java.util.List;

public final class PostDTO {
    private Long id;
    private String content;
    private UserDTO userDTO;
    private List<LikeDTO> likeDTOS;
    private List<CommentDTO> commentDTOS;

    protected PostDTO() {
    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        if (post.getUser() != null) {
            this.userDTO = new UserDTO(post.getUser());
        }
        if (post.getLikes() != null) {
            likeDTOS = new ArrayList<>();
            post.getLikes().forEach(likeModel -> this.likeDTOS.add(new LikeDTO(likeModel)));
        }
        if (post.getComments() != null) {
            commentDTOS = new ArrayList<>();
            post.getComments().forEach(commentModel -> this.commentDTOS.add(new CommentDTO(commentModel)));
        }
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public List<LikeDTO> getLikeDTOS() {
        return likeDTOS;
    }

    public void setLikeDTOS(List<LikeDTO> likeDTOS) {
        this.likeDTOS = likeDTOS;
    }

    public List<CommentDTO> getCommentDTOS() {
        return commentDTOS;
    }

    public void setCommentDTOS(List<CommentDTO> commentDTOS) {
        this.commentDTOS = commentDTOS;
    }
}