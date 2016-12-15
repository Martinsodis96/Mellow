package com.mellow.service;

import com.mellow.model.PostDao;
import com.mellow.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Iterable<PostDao> getAllPosts(){
        return postRepository.findAll();
    }

    public PostDao getPostById(Long id){
        return postRepository.findOne(id);
    }
}
