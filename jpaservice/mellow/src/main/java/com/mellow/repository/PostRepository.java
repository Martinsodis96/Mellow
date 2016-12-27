package com.mellow.repository;

import com.mellow.model.PostModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<PostModel, Long> {

    List<PostModel> findByUserId(Long userId);
}
