package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.Like;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
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
    public Like create(Like entity) {
        try {
            if(entity.getPost() != null && entity.getUser() != null){
                Post postModel = postRepository.findOne(entity.getPost().getId());
                User userModel = userRepository.findOne(entity.getUser().getId());
                if (postModel != null) {
                    if (userModel != null) {
                        return likeRepository.save(entity);
                    } else {
                        throw new NoSearchResultException("Could not find user" +
                                " with id: " + entity.getUser().getId());
                    }
                } else {
                    throw new NoSearchResultException("Could not find post" +
                            " with id: " + entity.getPost().getId());
                }
            }else{
                throw new InvalidInputException("Post or User can't be null");
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to add like to post");
        }
    }

    @Override
    public Like update(Like entity) {
        return null;
    }

    @Override
    public Like delete(Like entity) {
        try {
            if(entity != null && entity.getUser() != null && entity.getPost() != null){
                Like like = likeRepository.findOne(entity.getId());
                if (like != null) {
                    //TODO check if like has the same user as the like in the database.
                    likeRepository.delete(entity.getId());
                    return like;
                } else {
                    throw new NoSearchResultException("Could not find like with id: " + entity.getId());
                }
            }else{
                throw new InvalidInputException("Like, user or post can't be null");
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to remove like with id: " + entity.getId());
        }
    }

    public List<Like> getAllByPostId(Long postId){
        try {
            Post postModel = postRepository.findOne(postId);
            if (postModel != null) {
                return likeRepository.findByPostId(postId);
            } else {
                throw new NoSearchResultException("Could not find post" +
                        " with id: " + postId);
            }
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all likes");
        }
    }
}
