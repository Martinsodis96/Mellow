package com.mellow.resources;

import com.mellow.model.Profile;
import com.mellow.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("profiles")
@Controller
public class ProfileResource {

    @Autowired
    private ProfileService profileService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Profile> getProfiles() {
        return profileService.getAllProfiles();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Profile addProfile(Profile profile) {
        return profileService.createProfile(profile);
    }

    @PUT
    @Path("{profileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Profile updateProfile(@PathParam("profileId") Long id, Profile profile) {
        profile.setId(id);
        return profileService.updateProfile(profile);
    }

    @GET
    @Path("{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profile getProfileById(@PathParam("profileId") Long id) {
        return profileService.getById(id);
    }

    /*@GET
    @Path("{profileUsername}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profile getProfileById(@PathParam("profileUsername") String username){
        return profileService.getByUsername(username);
    }*/
}
