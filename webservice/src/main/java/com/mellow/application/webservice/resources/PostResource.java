package com.mellow.application.webservice.resources;

import com.mellow.application.webservice.model.Post;
import com.mellow.application.jpaservice.entity.model.PostModel;
import com.mellow.application.jpaservice.service.PostService;
import com.mellow.application.jpaservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Context
    private UriInfo uriInfo;

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostResource(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GET
    @Path("{postId}")
    public Post getPostById(@PathParam("postId") Long postId) {
        return new Post(postService.getPostById(postId));
    }

    @GET
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        postService.getAllPosts().forEach(postModel -> posts.add(new Post(postModel)));
        return posts;
    }

    @PUT
    @Path("{postId}")
    public Response updatePost(@PathParam("postId") Long postId, Post post) {
        PostModel createdPostModel = postService.updatePost(postId, post.getContent());
        return Response.noContent().location(URI.create(uriInfo.getPath() + "/" + createdPostModel.getId())).build();
    }

    @DELETE
    @Path("{postId}")
    public Response remove(@PathParam("postId") Long postId) {
        postService.removePost(postId);
        return Response.noContent().build();
    }
}
