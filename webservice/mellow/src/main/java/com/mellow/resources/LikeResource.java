package com.mellow.resources;

import com.mellow.model.Like;
import com.mellow.model.LikeModel;
import com.mellow.service.LikeService;
import com.mellow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

@Path("posts/{postId}/likes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LikeResource {

    @Context
    private UriInfo uriInfo;

    @PathParam("postId")
    private Long postId;
    private final PostService postService;

    private final LikeService likeService;

    @Autowired
    public LikeResource(PostService postService, LikeService likeService) {
        this.postService = postService;
        this.likeService = likeService;
    }

    @GET
    @Path("{likeId}")
    public Like getLike(@PathParam("likeId") Long likeId) {
        return new Like(likeService.getLikeById(likeId));
    }

    @GET
    public List<Like> getAllLikes() {
        List<Like> likes = new ArrayList<>();
        postService.getAllLikesFromPost(postId).forEach(likeModel ->
                likes.add(new Like(likeModel)));
        return likes;
    }

    @POST
    public Response addLikeToPost(Like like) {
        LikeModel createdLike = postService.addLikeToPost(postId, like.getUserId());
        return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdLike.getId())).build();
    }

    @DELETE
    @Path("{likeId}")
    public Response removeLikeFromPost(@PathParam("likeId") Long likeId) {
        postService.removeLikeFromPost(likeId);
        return Response.noContent().build();
    }
}
