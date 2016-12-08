package com.mellow.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("users")
public class UserResource {

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getAllUsers(){
        return null;
    }
}