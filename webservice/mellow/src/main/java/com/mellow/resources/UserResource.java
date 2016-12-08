package com.mellow.resources;

import com.mellow.model.PageRequestBean;
import com.mellow.model.User;
import com.mellow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("users")
public class UserResource {

    @Autowired
    private UserService userService;
    private final Long apiKey = 123791273L;
    @HeaderParam("api-key")
    String apiKeyParam;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@BeanParam() PageRequestBean pageRequestBean,
                                @QueryParam("linksOnly") boolean linksOnly) {
        List<URI> linkList = new ArrayList<>();
        if (linksOnly) {
            userService.getAllByPage(pageRequestBean.getPage(), pageRequestBean.getSize(),
                    "desc".equals(pageRequestBean.getSort()) ? UserService.SortType.DESC : UserService.SortType.ASC).forEach(user -> {
                linkList.add(uriInfo.getAbsolutePathBuilder().path(user.getId().toString()).build());
            });
            return Response.ok(linkList).build();
        } else {
            return Response.ok(userService.getAllByPage(pageRequestBean.getPage(),
                    pageRequestBean.getSize(), "desc".equals(pageRequestBean.getSort()) ? UserService.SortType.DESC : UserService.SortType.ASC)).build();
        }
    }

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") Long id) {
        if (this.apiKey.toString().equals(apiKeyParam)) {
            User user = userService.getById(id);
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User createdUser = userService.createUser(user);
        return Response.accepted(createdUser).status(Response.Status.CREATED)
                .header("location", "users/" + createdUser.getId()).build();
    }

    @PUT
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") Long id, String username) {
        User user = userService.updateUser(username, id);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long id) {
        User user = userService.deleteUser(id);
        return Response.ok(user).build();
    }
}