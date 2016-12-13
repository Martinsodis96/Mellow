package com.mellow.resources;

import com.mellow.model.Post;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("posts")
public class PostResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts(){
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("This is just a temp text!", "Test1", "Testsson1"));
        posts.add(new Post("This is a longer temp text to show how it will look in the future when someone type something longer!", "Test2", "Testsson2"));
        posts.add(new Post("This is just a temp text to show how it will look in the future3!", "Test3", "Testsson3"));
        posts.add(new Post("This is just a temp text to show how it will look in the future4!", "Test4", "Testsson4"));
        return Response.ok(new Post("This is just a temp text!", "Test1", "Testsson1")).build();
    }
}
