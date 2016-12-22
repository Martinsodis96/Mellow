package com.mellow.repository;

import com.mellow.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Long>{

    UserModel findByUsername(String username);
}
