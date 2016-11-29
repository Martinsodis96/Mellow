package com.mellow.resources;

import com.mellow.model.User;
import com.mellow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("users")
@Controller
public class UserResource {

    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        return Response.ok(users).build();
    }

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") Long id) {
        User user = userService.getById(id);
        return Response.ok(user).build();
    }

    @POST
    public Response addUser(String username, String firstname, String lastname) {
        User createdUser = userService.createUser(username, firstname, lastname);
        return Response.status(Response.Status.CREATED).header("location", "users/" + createdUser.getId()).build();
    }

    @PUT
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") Long id, String username) {
        userService.updateUser(username, id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long id) {
        try{
            User user = userService.deleteUser(id);
            return Response.ok(user).build();
        }catch (Exception e){
            return Response.status(404).build();
        }
    }
}
