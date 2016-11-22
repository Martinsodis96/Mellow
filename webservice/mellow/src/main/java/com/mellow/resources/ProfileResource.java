package com.mellow.resources;

import com.mellow.service.ProfileService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("profiles")
public class ProfileResource {

    private ProfileService profileService;

    public ProfileResource(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getProfiles() {
        return "test";
    }
}
