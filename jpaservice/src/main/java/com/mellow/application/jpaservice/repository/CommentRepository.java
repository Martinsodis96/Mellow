package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.model.CommentModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentModel, Long> {
    List<CommentModel> findByPostId(Long postId);
}
