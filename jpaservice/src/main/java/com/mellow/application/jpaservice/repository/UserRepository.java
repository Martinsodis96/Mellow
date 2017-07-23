package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);
}
