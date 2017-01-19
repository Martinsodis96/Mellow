package com.mellow.service;

import com.mellow.model.UserModel;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.InvalidInputException;
import com.mellow.service.exception.NoSearchResultException;
import com.mellow.service.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

import static com.mellow.service.SecurityHelper.generateSalt;
import static com.mellow.service.SecurityHelper.hashPassword;
import static com.mellow.service.SecurityHelper.hashingIterations;

@Service
public class AuthenticationService {

    private UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(String username, String password) {
        validateCredentials(username, password);
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return execute(userRepository1 -> userRepository.save(new UserModel(username,
                        hashedPassword, salt, hashingIterations)),
                String.format("Failed to create User with username: %s", username));
    }

    public String authenticateUser(String username, String password){
        checkCredentialsPresence(username, password);
        if(usernameExists(username)){
            UserModel user = userRepository.findByUsername(username);
            if(passwordMatches(password, user.getSalt(), user.getPassword())){
                //TODO return token
                return "Token";
            }
            throw new UnAuthorizedException("Wrong password");
        }
            throw new NoSearchResultException(String.format("Could not find user with username %s", username));
    }

    private boolean passwordMatches(String password, String salt, String storedPassword) {
        return storedPassword.equals(hashPassword(password, salt));
    }

    private void validateCredentials(String username, String password) {
        checkCredentialsPresence(username, password);
        if (username.length() < 4)
            throw new InvalidInputException("Username has to be at least 3 characters long");
        else if (usernameExists(username))
            throw new InvalidInputException("Username is already taken");
    }

    private void checkCredentialsPresence(String username, String password){
        if (!Optional.ofNullable(username).isPresent())
            throw new InvalidInputException("Missing username in body");
        else if (!Optional.ofNullable(password).isPresent())
            throw new InvalidInputException("Missing password in body");
    }

    private boolean usernameExists(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)).isPresent();
    }

    private UserModel execute(Function<UserRepository, UserModel> operation, String dbExMsg) {
        try {
            return operation.apply(userRepository);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new DatabaseException(dbExMsg);
        }
    }
}