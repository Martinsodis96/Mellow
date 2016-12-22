package com.mellow.repository;

import com.mellow.model.CommentModel;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentModel, Long> {
}
