package com.mellow.repository;

import com.mellow.model.LikeModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LikeRepository extends CrudRepository<LikeModel, Long> {

    LikeModel deleteByUserIdAndPostId(Long userId, Long postId);

    List<LikeModel> findByPostId(Long postId);
}
