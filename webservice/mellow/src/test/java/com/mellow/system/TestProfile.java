package com.mellow.system;

import com.mellow.config.JpaConfig;
import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import com.mellow.service.ProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.function.Function;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class})
public class TestProfile {

    @Autowired
    private ProfileService profileService;

    @Test
    public void canGetAllProfiles() {
        profileService.getAllProfiles();
    }
}
