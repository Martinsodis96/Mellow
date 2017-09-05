package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByUserId(Long userId);
}
