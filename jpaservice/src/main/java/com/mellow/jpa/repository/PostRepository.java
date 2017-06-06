package com.mellow.jpa.repository;

import com.mellow.jpa.entity.model.PostModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<PostModel, Long> {

    List<PostModel> findByUserId(Long userId);
}
