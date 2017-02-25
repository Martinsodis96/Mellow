package com.mellow.repository;

import com.mellow.model.LikeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<LikeModel, Long> {

    List<LikeModel> findByPostId(Long postId);
}
