package com.mellow.resources;

import com.mellow.model.Post;
import com.mellow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PostResource {

    @Autowired
    PostService postService;

    @GET
    public Response getAllPosts(){
        List<Post> posts = new ArrayList<>();
        postService.getAllPosts().forEach(postDao -> posts.add(new Post(postDao)));
        return Response.ok(posts).build();
    }
}
