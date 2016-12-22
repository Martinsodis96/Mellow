package com.mellow.service;

import com.mellow.model.PostModel;
import com.mellow.model.UserModel;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.InvalidInputException;
import com.mellow.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getById(Long userId) {
        return execute(userRepository1 -> userRepository1.findOne(userId),
                String.format("Failed to get User with id: %d", userId));
    }

    public Iterable<UserModel> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all users");
        }
    }

    public UserModel createUser(UserModel user) {
        if (user.getUsername().length() > 3) {
            return execute(userRepository1 -> userRepository1.save(user),
                    String.format("Failed to create User with username: %s", user.getUsername()));
        } else {
            throw new InvalidInputException("Username has to be at least 3 characters long.");
        }
    }

    public UserModel getByUsername(String username) {
        return execute(userRepository1 -> userRepository1.findByUsername(username),
                String.format("Failed to get User with username: %s", username));
    }

    public UserModel updateUser(String username, Long userId) {
        try {
            UserModel user = userRepository.findOne(userId);
            if(user != null){
                user.setUsername(username);
                return userRepository.save(user);
            }else {
                throw new NoSearchResultException(
                        String.format("user with id: %d do not exist.", userId));
            }
        }catch (DataAccessException e){
            throw new DatabaseException(
                    String.format("Failed to update user with id: %d", userId));
        }
    }

    public UserModel deleteUser(Long userId) {
        try {
            UserModel user = userRepository.findOne(userId);
            if(user != null){
                userRepository.delete(user);
                return user;
            }else {
                throw new NoSearchResultException(
                        String.format("user with id: %d do not exist.", userId));
            }
        }catch (DataAccessException e){
            throw new DatabaseException(
                    String.format("Failed to delete user with id: %d", userId));
        }
    }

    public Iterable<PostModel> getAllPostsFromUser(Long userId){
        try {
            UserModel user = userRepository.findOne(userId);
            if(user != null){
                return user.getPosts();
            }else {
                throw new NoSearchResultException(
                        String.format("user with id: %d do not exist.", userId));
            }
        }catch (DataAccessException e){
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
