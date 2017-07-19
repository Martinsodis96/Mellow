package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.LikeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<LikeModel, Long> {

    List<LikeModel> findByPostId(Long postId);
}
