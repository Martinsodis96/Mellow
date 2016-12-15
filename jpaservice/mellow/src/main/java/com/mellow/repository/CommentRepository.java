package com.mellow.repository;

import com.mellow.model.CommentDao;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<CommentDao, Long> {
}
