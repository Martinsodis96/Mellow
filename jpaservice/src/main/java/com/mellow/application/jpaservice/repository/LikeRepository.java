package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.Like;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<Like, Long> {

    List<Like> findByPostId(Long postId);
}
