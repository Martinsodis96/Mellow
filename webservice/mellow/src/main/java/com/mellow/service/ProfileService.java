package com.mellow.service;

import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProfileService {

    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Iterable<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    public Profile getByUsername(String username){
        return profileRepository.findByUsername(username);
    }

}
