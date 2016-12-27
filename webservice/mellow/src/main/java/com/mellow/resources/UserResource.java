package com.mellow.resources;

import com.mellow.model.User;
import com.mellow.model.UserDao;
import com.mellow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;

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

    @Autowired
    private UserService userService;

    @GET
    public Iterable<User> getAllUsers(@QueryParam("username") String username){
        List<User> users = new ArrayList<>();
        userService.getAllUsers().forEach(userDao -> users.add(new User(userDao)));
        return users;
    }

    @GET
    @Path("{userId}")
    public User getById(@PathParam("userId") Long userId){
        return new User(userService.getById(userId));
    }

    @POST
    public Response createUser(UserDao userDao){
        UserDao createdUserDao = userService.createUser(userDao);
        return Response.created(URI.create(uriInfo.getPath() + "/" + userDao.getId())).build();
    }

    @PUT
    @Path("{userId}")
    public Response updateUsername(UserDao userDao){
        //TODO update only username and ignore the rest.
        return null;
    }

    @DELETE
    @Path("{userId}")
    public User removeUser(){
        return null;
    }


}