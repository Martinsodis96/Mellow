package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.Like;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.repository.LikeRepository;
import com.mellow.application.jpaservice.repository.PostRepository;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.CrudService;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.ForbiddenException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService implements CrudService<Like> {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostRepository postRepository,
                       UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Like> getAll() {
        return null;
    }

    @Override
    public Like getById(Long id) {
        try {
            return likeRepository.findOne(id);
        } catch (DataAccessException e) {
            throw new DatabaseException("Could noy get like with id: " + id);
        }
    }

    @Override
    public Like create(Like like) {
        try {
            if (like != null && like.getPost() != null && like.getUser() != null) {
                Post post = postRepository.findOne(like.getPost().getId());
                User user = userRepository.findOne(like.getUser().getId());
                if (post != null) {
                    if (user != null) {
                        return likeRepository.save(like);
                    } else {
                        throw new NoSearchResultException("Could not find user" + " with id: " + like.getUser().getId());
                    }
                } else {
                    throw new NoSearchResultException("Could not find post" + " with id: " + like.getPost().getId());
                }
            } else {
                throw new InvalidInputException("Post or User can't be null");
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to add like to post");
        }
    }

    @Override
    public Like update(Like like) {
        return null;
    }

    @Override
    public Like delete(Like like) {
        if (like != null && like.getUser() != null && like.getPost() != null) {
            try {
                Like existingLike = likeRepository.findOne(like.getId());
                if (existingLike != null) {
                    if (existingLike.getUser().getId().equals(like.getUser().getId())) {
                        likeRepository.delete(like.getId());
                        return existingLike;
                    } else
                        throw new ForbiddenException("The user do not belong to the like with id: " + like.getId());
                } else {
                    throw new NoSearchResultException("Could not find like with id: " + like.getId());
                }
            } catch (DataAccessException e) {
                throw new DatabaseException("Failed to remove like with id: " + like.getId());
            }
        } else {
            throw new InvalidInputException("Like, user or post can't be null");
        }
    }

    public List<Like> getAllByPostId(Long postId) {
        try {
            Post postModel = postRepository.findOne(postId);
            if (postModel != null) {
                return likeRepository.findByPostId(postId);
            } else {
                throw new NoSearchResultException("Could not find post" + " with id: " + postId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all likes");
        }
    }
}
