package com.mellow.repository;

import com.mellow.entity.model.CommentModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentModel, Long> {
    List<CommentModel> findByPostId(Long postId);
}
