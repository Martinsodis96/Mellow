package com.mellow.application.jpaservice.service;

import com.mellow.application.jpaservice.config.ConfigHelper;
import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.entity.model.UserModel;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.mellow.application.jpaservice.service.helper.Authentication.checkCredentialsPresence;
import static com.mellow.application.jpaservice.service.helper.Authentication.createAccessToken;
import static com.mellow.application.jpaservice.service.helper.Authentication.createRefreshToken;
import static com.mellow.application.jpaservice.service.helper.Authentication.generateSalt;
import static com.mellow.application.jpaservice.service.helper.Authentication.hashPassword;
import static com.mellow.application.jpaservice.service.helper.Authentication.hashingIterations;
import static com.mellow.application.jpaservice.service.helper.Authentication.passwordMatches;
import static com.mellow.application.jpaservice.service.helper.Authentication.validateCredentials;
import static com.mellow.application.jpaservice.service.helper.Authentication.validateToken;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private ConfigHelper configHelper;
    private final String issuer = "https://stormpath.com/";

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.configHelper = new ConfigHelper("config.properties");
    }

    public UserModel createUser(Credentials credentials) {
        checkCredentialsPresence(credentials);
        validateCredentials(credentials, userRepository);
        String salt = generateSalt();
        String hashedPassword = hashPassword(credentials.getPassword(), salt);
        return execute(userRepository -> userRepository.save(new UserModel(credentials.getUsername(),
                        hashedPassword, salt, hashingIterations)),
                String.format("Failed to create User with username: %s", credentials.getUsername()));
    }

    public UserModel authenticateUser(Credentials credentials) {
        checkCredentialsPresence(credentials);
        Optional<UserModel> optionalUser = userRepository.findByUsername(credentials.getUsername());
        if (optionalUser.isPresent()) {
            if (passwordMatches(credentials.getPassword(), optionalUser.get().getSalt(), optionalUser.get().getPassword())) {
                return optionalUser.get();
            }
            throw new UnAuthorizedException("Wrong password");
        }
        throw new NoSearchResultException(String.format("Could not find user with username %s", credentials.getUsername()));
    }

    public void validateAccessToken(String token) {
        validateToken(token, "Access Token", issuer, configHelper.getJwtAccessSecretValue());
    }

    public void validateRefreshToken(String token) {
        validateToken(token, "Refresh Token", issuer, configHelper.getJwtRefreshSecretValue());
    }

    public String getAccessToken() {
        return createAccessToken("Access Token", DateUtils.addHours(new Date(), 2), configHelper.getJwtAccessSecretValue());
    }

    public String getRefreshToken() {
        return createRefreshToken("Refresh Token", configHelper.getJwtRefreshSecretValue());
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