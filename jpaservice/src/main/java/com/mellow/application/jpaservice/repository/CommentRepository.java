package com.mellow.application.jpaservice.repository;

import com.mellow.application.jpaservice.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);
}
