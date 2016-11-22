package com.mellow.service;

import com.mellow.model.Profile;
import com.mellow.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Iterable<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

}
