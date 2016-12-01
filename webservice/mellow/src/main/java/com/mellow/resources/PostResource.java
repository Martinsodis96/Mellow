package com.mellow.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts")
public class PostResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts(){
        return null;
    }
}
