package com.mellow.jpa.repository;

import com.mellow.jpa.entity.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    UserModel findByUsername(String username);
}
