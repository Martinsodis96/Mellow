package com.mellow.resources;

import com.mellow.config.JpaConfig;
import com.mellow.model.Profile;
import org.springframework.test.context.ContextConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("profiles")
@ContextConfiguration(classes = {JpaConfig.class})
public class ProfileResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Iterable<Profile> getProfiles() {
        return null;
    }

    @GET
    @Path("{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profile getProfile(@PathParam("profileId") String username){
        return null;
    }
}
