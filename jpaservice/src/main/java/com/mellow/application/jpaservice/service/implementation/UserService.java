package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.entity.Post;
import com.mellow.application.jpaservice.entity.User;
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
import java.util.Optional;
import java.util.function.Function;

import static com.mellow.application.jpaservice.service.helper.Authentication.checkCredentialsPresence;
import static com.mellow.application.jpaservice.service.helper.Authentication.generateSalt;
import static com.mellow.application.jpaservice.service.helper.Authentication.hashPassword;
import static com.mellow.application.jpaservice.service.helper.Authentication.hashingIterations;
import static com.mellow.application.jpaservice.service.helper.Authentication.validateCredentials;

@Service
public class UserService implements CrudService<User>{

    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
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
    public User create(User entity) {
        Credentials credentials = new Credentials(entity.getUsername(), entity.getPassword());
        checkCredentialsPresence(credentials);
        validateCredentials(credentials, userRepository);
        String salt = generateSalt();
        String hashedPassword = hashPassword(credentials.getPassword(), salt);
        return execute(userRepository -> userRepository.save(new User(credentials.getUsername(),
                        hashedPassword, salt, hashingIterations)),
                String.format("Failed to create User with username: %s", credentials.getUsername()));
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public User delete(User entity) {
        return execute(userRepository -> {
            if(entity != null){
                User user = userRepository.findOne(entity.getId());
                if (user != null) {
                    userRepository.delete(user);
                    return user;
                } else {
                    throw new NoSearchResultException(
                            String.format("user with id: %d do not exist.", entity.getId()));
                }
            }else {
                throw new InvalidInputException("User can't be null");
            }
        }, String.format("Failed to remove user with id: %d", entity.getId()));
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
            throw new DatabaseException(dbExMsg);
        }
    }
}
