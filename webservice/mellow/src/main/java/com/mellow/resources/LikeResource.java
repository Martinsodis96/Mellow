package com.mellow.resources;

import com.mellow.model.Like;
import com.mellow.model.LikeModel;
import com.mellow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("posts/{postId}/likes")
public class LikeResource {

    @Context
    private UriInfo uriInfo;

    @PathParam("postId")
    private Long postId;
    private final PostService postService;

    @Autowired
    public LikeResource(PostService postService) {
        this.postService = postService;
    }

    @GET
    public List<Like> getAllLikes(){
        List<Like> likes = new ArrayList<>();
        postService.getAllLikesFromPost(postId).forEach(likeModel ->
                likes.add(new Like(likeModel)));
        return likes;
    }

    @POST
    public Response addLikeToPost(Like like){
        LikeModel createdLike = postService.addLikeToPost(postId, like.getUserId());
        return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdLike.getId())).build();
    }

    @DELETE
    @Path("{likeId}")
    public Response removeLikeFromPost(@PathParam("likeId") Long likeId){
        postService.removeLikeFromPost(likeId);
        return Response.noContent().build();
    }
}
