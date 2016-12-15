package com.mellow.repository;

import com.mellow.model.UserDao;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDao, Long>{
}
