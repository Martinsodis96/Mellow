package com.mellow.service;

import com.mellow.model.LikeDao;
import com.mellow.model.PostDao;
import com.mellow.model.UserDao;
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

    public PostDao getPostById(Long postId) {
        PostDao post = execute(postRepository1 ->
                        postRepository1.findOne(postId),
                "Failed to get post with id: " + postId);
        if (post != null) {
            return post;
        } else {
            throw new NoSearchResultException("Could not find post with id: " + postId);
        }
    }

    public Iterable<PostDao> getAllPosts() {
        Iterable<PostDao> posts;
        try {
            posts = postRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all posts");
        }

        if (posts != null) {
            return posts;
        } else {
            throw new NoSearchResultException("No posts were found");
        }
    }

    public PostDao createPost(Long userId, String content) {
        UserDao user;
        try {
            user = userRepository.findOne(userId);
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to create post");
        }

        if (user != null) {
            return postRepository.save(new PostDao(content, user));
        } else {
            throw new NoSearchResultException("Could not find user with id: " + userId);
        }
    }

    public PostDao updatePost(Long postId, String content) {
        PostDao post = execute(postRepository1 ->
                        postRepository1.findOne(postId),
                "Failed to update post with id: " + postId);

        if (post != null) {
            return postRepository.save(post.setContent(content));
        } else {
            throw new NoSearchResultException("Could not find post with id: " + postId);
        }
    }

    public PostDao removePost(Long postId) {
        PostDao post = execute(postRepository1 ->
                        postRepository1.findOne(postId),
                "Failed to remove post with id: " + postId);

        if (post != null) {
            postRepository.delete(postId);
            return post;
        } else {
            throw new NoSearchResultException("Could not find post with id: " + postId);
        }
    }

    private PostDao execute(Function<PostRepository, PostDao> operation, String dbExMsg) {
        try {
            return operation.apply(postRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}