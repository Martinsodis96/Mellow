package com.mellow.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/chats")
public class ChatResource {

    @PathParam("userId")
    private String userId;

    @GET
    public Response getAllChats() {
        return null;
    }
}
