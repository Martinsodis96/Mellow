package com.mellow.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("posts/{postId}/comments")
public class CommentResource {

    @PathParam("postId")
    private String postId;

    @GET
    public Response getAllComments(){
        return null;
    }
}
