package com.mellow.resources;

import com.mellow.model.Comment;
import com.mellow.entity.model.CommentModel;
import com.mellow.service.CommentService;
import com.mellow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.BadRequestException;
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

    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public CommentResource(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GET
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        postService.getAllCommentsFromPost(postId)
                .forEach(commentModel -> comments.add(new Comment(commentModel)));
        return comments;
    }

    @POST
    public Response createComment(Comment comment) {
        //TODO move null checks to the service layer.
        if (comment.getContent() != null && comment.getUser() != null) {
            CommentModel createdComment = commentService.createComment(comment.getContent(), postId, comment.getUser().getId());
            return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdComment.getId())).build();
        } else throw new BadRequestException();
    }
}
