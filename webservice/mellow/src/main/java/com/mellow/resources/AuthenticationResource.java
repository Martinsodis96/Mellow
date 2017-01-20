package com.mellow.resources;

import com.mellow.model.User;
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
    public Response createUser(User user){
        //TODO Create a new User and return a token from the service
        String string = authenticationService.createUser(user.getUsername(), user.getPassword());
        return Response.ok(string).build();
    }

    @POST
    @Path("login")
    public Response authenticateUser(User user){
        String token = authenticationService.authenticateUser(user.getUsername(), user.getPassword());
        return Response.ok(token).build();
    }
}
