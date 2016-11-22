package com.mellow.resources;

import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import com.mellow.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("profiles")
public class ProfileResource {

    @Autowired
    private ProfileService profileService;

    public ProfileResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Iterable<Profile> getProfiles() {
        return profileService.getAllProfiles();
    }
}
