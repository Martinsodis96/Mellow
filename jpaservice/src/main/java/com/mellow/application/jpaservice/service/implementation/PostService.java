package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.Comment;
import com.mellow.application.jpaservice.entity.Like;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.repository.CommentRepository;
import com.mellow.application.jpaservice.repository.LikeRepository;
import com.mellow.application.jpaservice.repository.PostRepository;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.CrudService;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class PostService implements CrudService<Post> {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository,
                       CommentRepository commentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Post> getAll() {
        try {
            return (List<Post>) postRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all posts");
        }
    }

    @Override
    public Post getById(Long id) {
        return execute(postRepository1 -> postRepository1.findOne(id),
                "Failed to get post with id: " + id);
    }

    @Override
    public Post create(Post entity) {
        if (entity != null && entity.getContent() != null && entity.getUser() != null) {
            return execute(postRepository -> {
                User user = userRepository.findOne(entity.getUser().getId());
                if (user != null) {
                    return postRepository.save(entity);
                } else {
                    throw new NoSearchResultException("Could not find user with id: " + entity.getUser().getId());
                }
            }, "Failed to create post");
        } else {
            throw new InvalidInputException("Post content or user can't be null");
        }
    }

    @Override
    public Post update(Post entity) {
        if (entity != null && entity.getContent() != null && entity.getId() != null) {
            return execute(postRepository -> {
                Post post = postRepository.findOne(entity.getId());
                if (post != null) {
                    return postRepository.save(post.setContent(entity.getContent()));
                } else {
                    throw new NoSearchResultException("Could not find post with id: " + entity.getId());
                }
            }, "Failed to update post with id: " + entity.getId());
        } else {
            throw new InvalidInputException("Post content or id can't be null");
        }
    }

    @Override
    public Post delete(Post entity) {
        return execute(postRepository -> {
            if (entity != null && entity.getUser() != null) {
                Post post = postRepository.findOne(entity.getId());
                if (post != null) {
                    //TODO check if post has the same user as the post in the database.
                    postRepository.delete(entity.getId());
                    return post;
                } else {
                    throw new NoSearchResultException("Could not find post with id: " + entity.getId());
                }
            } else {
             throw new InvalidInputException("Post or user can't be null");
            }
        }, "Failed to remove post with id: " + entity.getId());
    }

    public List<Post> getAllByUserId(Long userId) {
        try {
            User user = userRepository.findOne(userId);
            if (user != null) {
                return postRepository.findByUserId(userId);
            } else {
                throw new NoSearchResultException(
                        String.format("user with id: %d do not exist.", userId));
            }
        } catch (DataAccessException e) {
            throw new DatabaseException(
                    String.format("Failed to delete user with id: %d", userId));
        }
    }

    private Post execute(Function<PostRepository, Post> operation, String dbExMsg) {
        try {
            return operation.apply(postRepository);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DatabaseException(dbExMsg);
        }
    }
}