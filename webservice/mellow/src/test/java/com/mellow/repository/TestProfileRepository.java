package com.mellow.repository;

import com.mellow.model.Profile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.function.Function;

public final class TestProfileRepository {

    private final String projectPackage = "com.mellow";

    @Test
    public void canGetAllProfiles() throws Exception {
        Iterable<Profile> profiles = executeMany(profileRepository -> profileRepository.findAll());
        profiles.forEach(profile -> System.out.println(profile.getUsername()));
    }

    public Iterable<Profile> executeMany(Function<ProfileRepository, Iterable<Profile>> operation) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
            context.scan(projectPackage);
            context.refresh();
            ProfileRepository profileRepository = context.getBean(ProfileRepository.class);
            return operation.apply(profileRepository);
        }
    }
}
