package com.mellow.webservice.resources;

import com.mellow.entity.Credentials;
import com.mellow.webservice.model.User;
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
import java.net.URI;

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
    public Response createUser(Credentials credentials) {
        User user = new User(authenticationService.createUser(credentials));
        return Response.created(URI.create("users/" + user.getId())).build();
    }

    @POST
    @Path("login")
    public Response authenticateUser(Credentials credentials) {
        User user = new User(authenticationService.authenticateUser(credentials));
        return Response.ok(user).build();
    }

    @POST
    @Path("auth")
    public Response authenticateToken() {
        return Response.ok().build();
    }
}
