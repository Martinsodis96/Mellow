package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.CrudService;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserService implements CrudService<User> {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        try {
            return (List<User>) userRepository.findAll();
        } catch (DataAccessException e) {
            throw new DatabaseException("Failed to get all users");
        }
    }

    @Override
    public User getById(Long userId) {
        return execute(userRepository -> userRepository.findOne(userId),
                String.format("Failed to get User with id: %d", userId));
    }

    @Override
    public User create(User user) {
        return execute(userRepository -> {
            if (user != null) {
                if (user.getUsername().length() > 3) {
                    Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
                    if (!optionalUser.isPresent()) {
                        userRepository.save(user);
                        return user;
                    } else {
                        throw new InvalidInputException(
                                String.format("user with username: %s already exists.", user.getUsername()));
                    }
                } else
                    throw new InvalidInputException("Username has to be at least 3 characters long");
            } else {
                throw new InvalidInputException("User can't be null");
            }
        }, String.format("Failed to create User with username: %s", user.getUsername()));
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return execute(userRepository -> {
            if (user != null) {
                User existingUser = userRepository.findOne(user.getId());
                if (existingUser != null) {
                    userRepository.delete(user);
                    return user;
                } else {
                    throw new NoSearchResultException(
                            String.format("user with id: %d do not exist.", user.getId()));
                }
            } else {
                throw new InvalidInputException("User can't be null");
            }
        }, String.format("Failed to remove user with id: %d", user.getId()));
    }

    public User getByUsername(String username) {
        return execute(userRepository -> {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent())
                return optionalUser.get();
            else
                throw new NoSearchResultException("User with username " + username + " do not exist.");
        }, String.format("Failed to get User with username: %s", username));
    }

    public User updateUsername(String username, Long userId) {
        if (username != null) {
            return execute(userRepository -> {
                User user = userRepository.findOne(userId);
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

    private User execute(Function<UserRepository, User> operation, String dbExMsg) {
        try {
            return operation.apply(userRepository);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DatabaseException(dbExMsg);
        }
    }
}
