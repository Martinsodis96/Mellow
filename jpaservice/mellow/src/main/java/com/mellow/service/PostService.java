package com.mellow.service;

import com.mellow.model.Post;
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

    public Iterable<Post> getAllPosts(){
        return postRepository.findAll();
    }
}
