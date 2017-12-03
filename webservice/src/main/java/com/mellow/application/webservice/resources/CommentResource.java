package com.mellow.application.webservice.resources;

import com.mellow.application.jpaservice.entity.Comment;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.service.implementation.CommentService;
import com.mellow.application.webservice.model.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("posts/{postId}/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

    @PathParam("postId")
    private Long postId;

    @Context
    UriInfo uriInfo;

    private final CommentService commentService;

    @Autowired
    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }

    @GET
    public List<CommentDTO> getAllComments() {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        commentService.getbyPostId(postId)
                .forEach(commentModel -> commentDTOS.add(new CommentDTO(commentModel)));
        return commentDTOS;
    }

    @POST
    public Response createComment(CommentDTO commentDTO) {
        User user = new User().setId(commentDTO.getUserId());
        Post post = new Post().setId(postId);
        Comment comment = new Comment(commentDTO.getContent(), post, user);
        Comment createdComment = commentService.create(comment);
        return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdComment.getId())).build();
    }
}
