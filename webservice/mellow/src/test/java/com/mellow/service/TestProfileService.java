package com.mellow.service;

import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Function;

public class TestProfileService {

    private ProfileService profileService;
    private ProfileRepository profileRepository;
    private final String projectPackage = "com.mellow";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void canGetAllProfiles() throws Exception {
        Iterable<Profile> profiles = executeMany(profileService1 -> profileService1.getAllProfiles());
        profiles.forEach(profile -> System.out.println(profile.getUsername()));
    }

    private Iterable<Profile> executeMany(Function<ProfileService, Iterable<Profile>> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan(projectPackage);
            context.refresh();
            this.profileRepository = context.getBean(ProfileRepository.class);
            profileService = new ProfileService(profileRepository);
            return operation.apply(profileService);
        }
    }
}
