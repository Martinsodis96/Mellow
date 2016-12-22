package com.mellow.repository;

import com.mellow.model.PostModel;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostModel, Long> {

}
