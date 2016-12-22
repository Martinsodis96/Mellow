package com.mellow.service;

import com.mellow.model.PostModel;
import com.mellow.model.UserModel;
import com.mellow.repository.PostRepository;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostModel getPostById(Long postId) {
        return execute(postRepository1 -> postRepository1.findOne(postId),
                "Failed to get post with id: " + postId);
    }

    public Iterable<PostModel> getAllPosts() {
        Iterable<PostModel> posts;
        try {
            return postRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all posts");
        }
    }

    public PostModel createPost(Long userId, String content) {
        UserModel user;
        try {
            user = userRepository.findOne(userId);
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to create post");
        }

        if (user != null) {
            return postRepository.save(new PostModel(content, user));
        } else {
            throw new NoSearchResultException("Could not find user with id: " + userId);
        }
    }

    public PostModel updatePost(Long postId, String content) {
        PostModel post = execute(postRepository1 -> postRepository1.findOne(postId),
                "Failed to update post with id: " + postId);

        if (post != null) {
            return postRepository.save(post.setContent(content));
        } else {
            throw new NoSearchResultException("Could not find post with id: " + postId);
        }
    }

    public PostModel removePost(Long postId) {
        PostModel post = execute(postRepository1 -> postRepository1.findOne(postId),
                "Failed to remove post with id: " + postId);

        if (post != null) {
            postRepository.delete(postId);
            return post;
        } else {
            throw new NoSearchResultException("Could not find post with id: " + postId);
        }
    }

    private PostModel execute(Function<PostRepository, PostModel> operation, String dbExMsg) {
        try {
            return operation.apply(postRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}