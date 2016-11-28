package com.mellow.service;

import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    private ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Iterable<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getByUsername(String username){
        return profileRepository.findByUsername(username);
    }

    public Profile getById(Long id){
        return profileRepository.findOne(id);
    }

    public Profile updateProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public Profile createProfile(Profile profile){
        return profileRepository.save(profile);
    }
}
