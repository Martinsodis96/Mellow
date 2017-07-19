package com.mellow.webservice.resources;

import com.mellow.webservice.model.Post;
import com.mellow.application.jpaservice.entity.model.PostModel;
import com.mellow.webservice.model.User;
import com.mellow.application.jpaservice.entity.model.UserModel;
import com.mellow.application.jpaservice.service.PostService;
import com.mellow.application.jpaservice.service.UserService;
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
    public User getById(@PathParam("userId") Long userId) {
        return new User(userService.getById(userId));
    }

    @GET
    public Iterable<User> getAllUsers(@QueryParam("username") String username) {
        List<User> users = new ArrayList<>();
        userService.getAllUsers().forEach(userDao -> users.add(new User(userDao)));
        //TODO make a method in jpa to filter all users by username.
        return users;
    }

    @POST
    @Path("/{userId}/posts")
    public Response createPost(@PathParam("userId") Long userId, Post post) {
        PostModel createdPostModel = postService.createPost(userId, post.getContent());
        return Response.created(URI.create(uriInfo.getPath() + "/" + createdPostModel.getId())).build();
    }

    @GET
    @Path("/{userId}/posts")
    public List<Post> getAllPostsFromUser(@PathParam("userId") Long userId) {
        List<Post> posts = new ArrayList<>();
        userService.getAllPostsFromUser(userId)
                .forEach(postModel -> posts.add(new Post(postModel)));
        return posts;
    }

    @PUT
    @Path("{userId}")
    public Response updateUsername(@PathParam("userId") Long userId, UserModel userModel) {
        userService.updateUsername(userModel.getUsername(), userId);
        return Response.noContent().location(URI.create(uriInfo.getPath() + "/" + userId)).build();
    }

    @DELETE
    @Path("{userId}")
    public User removeUser(@PathParam("userId") Long userId) {
        return new User(userService.deleteUser(userId));
    }

}