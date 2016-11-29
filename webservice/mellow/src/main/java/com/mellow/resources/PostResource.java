package com.mellow.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("posts")
public class PostResource {

    @GET
    public Response getAllPosts(){
        return null;
    }
}
