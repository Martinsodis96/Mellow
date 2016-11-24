package com.mellow.service;

import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class TestProfileService {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;
    private Profile profile;
    private Long profileId;
    private List<Profile> profilesInDb;

    @Before
    public void setUp() {
        this.profile = new Profile("name");
        this.profileId = 1L;
        this.profilesInDb = new ArrayList<>();
        createProfiles(5);
    }

    @Test
    public void canGetAllProfiles() {
        profileService.getAllProfiles();
        verify(profileRepository).findAll();
    }

    private void createProfiles(int amount) {
        for (int i = 0; i < amount; i++) {
            profilesInDb.add(new Profile("test" + i));
        }
    }
}
