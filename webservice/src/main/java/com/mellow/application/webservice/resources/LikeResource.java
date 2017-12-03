package com.mellow.application.webservice.resources;

import com.mellow.application.jpaservice.entity.Like;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.service.implementation.LikeService;
import com.mellow.application.webservice.model.LikeDTO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("posts/{postId}/likes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LikeResource {

    @Context
    private UriInfo uriInfo;

    @PathParam("postId")
    private Long postId;

    private final LikeService likeService;

    @Autowired
    public LikeResource(LikeService likeService) {
        this.likeService = likeService;
    }

    @GET
    @Path("{likeId}")
    public LikeDTO get(@PathParam("likeId") Long likeId) {
        return new LikeDTO(likeService.getById(likeId));
    }

    @GET
    public List<LikeDTO> getAll() {
        List<LikeDTO> likeDTOS = new ArrayList<>();
        likeService.getAllByPostId(postId).forEach(likeModel ->
                likeDTOS.add(new LikeDTO(likeModel)));
        return likeDTOS;
    }

    @POST
    public Response create(LikeDTO likeDTO) {
        User user = new User().setId(likeDTO.getUserId());
        Post post = new Post().setId(postId);
        Like like = new Like(post, user);
        Like createdLike = likeService.create(like);
        return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + createdLike.getId())).build();
    }

    @DELETE
    @Path("{likeId}")
    public Response delete(@PathParam("likeId") Long likeId, LikeDTO likeDTO) {
        User user = new User().setId(likeDTO.getUserId());
        Post post = new Post().setId(postId);
        Like like = new Like(post, user).setId(likeId);
        likeService.delete(like);
        return Response.noContent().build();
    }
}
