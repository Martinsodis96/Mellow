package com.mellow.resources;

import com.mellow.model.Profile;
import com.mellow.service.ProfileService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("profiles")
public class ProfileResource {

    private ProfileService profileService;


    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Iterable<Profile> getProfiles() {
        return new ArrayList<>();
    }
}
