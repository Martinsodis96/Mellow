package com.mellow.application.webservice.resources;

import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.service.implementation.PostService;
import com.mellow.application.jpaservice.service.implementation.UserService;
import com.mellow.application.webservice.model.PostDTO;
import com.mellow.application.webservice.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    public UserDTO getById(@PathParam("userId") Long userId) {
        return new UserDTO(userService.getById(userId));
    }

    @GET
    public List<UserDTO> getAllUsers(@QueryParam("username") String username) {
        List<UserDTO> userDTOS = new ArrayList<>();
        userService.getAll().forEach(userDao -> userDTOS.add(new UserDTO(userDao)));
        return userDTOS;
    }

    @PUT
    @Path("{userId}")
    public Response updateUsername(@PathParam("userId") Long userId, UserDTO userDTO) {
        userService.updateUsername(userDTO.getUsername(), userId);
        return Response.noContent().location(URI.create(uriInfo.getPath() + "/" + userId)).build();
    }

    @DELETE
    @Path("{userId}")
    public UserDTO removeUser(@PathParam("userId") Long userId) {
        return new UserDTO(userService.delete(new User().setId(userId)));
    }

    @POST
    @Path("/{userId}/posts")
    public Response createPost(@PathParam("userId") Long userId, PostDTO postDTO) {
        User user = new User().setId(userId);
        Post post = new Post(postDTO.getContent(), user);
        Post createdPostModel = postService.create(post);
        return Response.created(URI.create(uriInfo.getPath() + "/" + createdPostModel.getId())).build();
    }

    @GET
    @Path("/{userId}/posts")
    public List<PostDTO> getAllPostsFromUser(@PathParam("userId") Long userId) {
        List<PostDTO> postDTOS = new ArrayList<>();
        postService.getAllByUserId(userId)
                .forEach(post -> postDTOS.add(new PostDTO(post)));
        return postDTOS;
    }
}