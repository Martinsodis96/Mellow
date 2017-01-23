package com.mellow.resources;

import com.mellow.model.Credentials;
import com.mellow.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    public AuthenticationResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("register")
    public Response createUser(Credentials credentials){
        String jwt = authenticationService.createUser(credentials);
        return Response.ok(jwt).build();
    }

    @POST
    @Path("login")
    public Response authenticateUser(Credentials credentials){
        String token = authenticationService.authenticateUser(credentials);
        return Response.ok(token).build();
    }
}
