package com.mellow.application.webservice.resources;

import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.service.implementation.PostService;
import com.mellow.application.webservice.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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

@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Context
    private UriInfo uriInfo;

    private final PostService postService;

    @Autowired
    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @GET
    @Path("{postId}")
    public PostDTO getPostById(@PathParam("postId") Long postId) {
        return new PostDTO(postService.getById(postId));
    }

    @GET
    public List<PostDTO> getAllPosts() {
        List<PostDTO> postDTOS = new ArrayList<>();
        postService.getAll().forEach(post -> postDTOS.add(new PostDTO(post)));
        return postDTOS;
    }

    @PUT
    @Path("{postId}")
    public Response updatePost(@PathParam("postId") Long postId, PostDTO postDTO) {
        Post post = new Post().setId(postId).setContent(postDTO.getContent());
        Post createdPost = postService.update(post);
        return Response.noContent().location(URI.create(uriInfo.getPath() + "/" + createdPost.getId())).build();
    }

    @DELETE
    @Path("{postId}")
    public Response remove(@PathParam("postId") Long postId) {
        postService.delete(new Post().setId(postId));
        return Response.noContent().build();
    }
}
