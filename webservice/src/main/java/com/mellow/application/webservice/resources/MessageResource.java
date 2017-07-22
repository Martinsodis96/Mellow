package com.mellow.application.webservice.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("users/{userId}/chats/{chatId}/messages")
public class MessageResource {

    @PathParam("chatId")
    private String chatId;

    @PathParam("userId")
    private String userId;

    @GET
    public Response getAllMessages() {
        return null;
    }
}
