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
    public Response addLikeToPost(LikeModel likeModel){
        LikeModel createdLike = postService.addLikeToPost(postId, likeModel);
        return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdLike.getId())).build();
    }
/*
    @DELETE
    public Response deleteLikeToPost(LikeModel likeModel){
        postService.removeLikeToPost(postId);
        return Response.created(uriInfo.getAbsolutePath()).build();
    }*/
}
