package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    UserModel findByUsername(String username);
}
