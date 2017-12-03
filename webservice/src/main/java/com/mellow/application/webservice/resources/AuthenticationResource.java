package com.mellow.application.webservice.resources;

import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.service.implementation.AuthenticationService;
import com.mellow.application.jpaservice.service.implementation.UserService;
import com.mellow.application.webservice.model.UserDTO;
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
    public AuthenticationResource(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
    }

    @POST
    @Path("register")
    public Response createUser(Credentials credentials) {
        UserDTO user = new UserDTO(authenticationService.createUser(credentials));
        return Response.created(URI.create("users/" + user.getId())).build();
    }

    @POST
    @Path("login")
    public Response authenticateUser(Credentials credentials) {
        UserDTO userDTO = new UserDTO(authenticationService.authenticateUser(credentials));
        return Response.ok(userDTO).build();
    }

    @POST
    @Path("auth")
    public Response authenticateToken() {
        return Response.ok().build();
    }
}
