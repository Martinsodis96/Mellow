package com.mellow.service;

import com.mellow.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public class ProfileService {

    public List<Profile> getAllProfiles(){
        ArrayList<Profile> profiles = new ArrayList<>();
        profiles.add(new Profile());
        return profiles;
    }

}
