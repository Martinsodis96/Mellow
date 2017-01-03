package com.mellow.resources;

import com.mellow.model.User;
import com.mellow.model.UserModel;
import com.mellow.service.PostService;
import com.mellow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Context
    private UriInfo uriInfo;

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserResource(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GET
    @Path("{userId}")
    public User getById(@PathParam("userId") Long userId){
        return new User(userService.getById(userId));
    }

    @GET
    public Iterable<User> getAllUsers(@QueryParam("username") String username){
        List<User> users = new ArrayList<>();
        userService.getAllUsers().forEach(userDao -> users.add(new User(userDao)));
        //TODO make a method in jpa to filter all users by username.
        return users;
    }

    @POST
    public Response createUser(User user){
        UserModel createdUserModel = userService.createUser(user.getUsername());
        return Response.created(URI.create(uriInfo.getPath() + "/" + createdUserModel.getId())).build();
    }

    @PUT
    @Path("{userId}")
    public Response updateUsername(@PathParam("userId") Long userId, UserModel userModel){
        userService.updateUsername(userModel.getUsername(), userId);
        return Response.noContent().location(URI.create(uriInfo.getPath() + "/" + userId)).build();
    }

    @DELETE
    @Path("{userId}")
    public User removeUser(@PathParam("userId") Long userId){
        return new User(userService.deleteUser(userId));
    }

}