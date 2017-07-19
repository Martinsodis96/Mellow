package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.PostModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<PostModel, Long> {

    List<PostModel> findByUserId(Long userId);
}
