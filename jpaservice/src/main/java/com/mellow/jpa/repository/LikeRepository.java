package com.mellow.jpa.repository;

import com.mellow.jpa.entity.model.LikeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<LikeModel, Long> {

    List<LikeModel> findByPostId(Long postId);
}
