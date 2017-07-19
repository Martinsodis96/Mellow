package com.mellow.application.jpaservice.service;

import com.mellow.application.jpaservice.entity.model.PostModel;
import com.mellow.application.jpaservice.entity.model.UserModel;
import com.mellow.application.jpaservice.repository.PostRepository;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserService {

    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public UserModel getById(Long userId) {
        return execute(userRepository -> userRepository.findOne(userId),
                String.format("Failed to get User with id: %d", userId));
    }

    public Iterable<UserModel> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all users");
        }
    }

    public UserModel getByUsername(String username) {
        return execute(userRepository -> userRepository.findByUsername(username),
                String.format("Failed to get User with username: %s", username));
    }

    public UserModel updateUsername(String username, Long userId) {
        if (username != null) {
            return execute(userRepository -> {
                UserModel user = userRepository.findOne(userId);
                if (user != null) {
                    user.setUsername(username);
                    return userRepository.save(user);
                } else {
                    throw new NoSearchResultException(
                            String.format("user with id: %d do not exist.", userId));
                }
            }, String.format("Failed to update user with id: %d", userId));
        } else {
            throw new InvalidInputException("Username can't be null");
        }
    }

    public UserModel deleteUser(Long userId) {
        return execute(userRepository -> {
            UserModel user = userRepository.findOne(userId);
            if (user != null) {
                userRepository.delete(user);
                return user;
            } else {
                throw new NoSearchResultException(
                        String.format("user with id: %d do not exist.", userId));
            }
        }, String.format("Failed to remove user with id: %d", userId));
    }

    public Iterable<PostModel> getAllPostsFromUser(Long userId) {
        try {
            UserModel user = userRepository.findOne(userId);
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

    private UserModel execute(Function<UserRepository, UserModel> operation, String dbExMsg) {
        try {
            return operation.apply(userRepository);
        } catch (DataAccessException e) {
            throw new DatabaseException(dbExMsg);
        }
    }
}
