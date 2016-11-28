package com.mellow.repository;

import com.mellow.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long>{

    Profile findByUsername(String username);

}
