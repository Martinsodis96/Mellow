package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.config.PropertyReader;
import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.mellow.application.jpaservice.service.helper.Authentication.checkCredentialsPresence;
import static com.mellow.application.jpaservice.service.helper.Authentication.createAccessToken;
import static com.mellow.application.jpaservice.service.helper.Authentication.createRefreshToken;
import static com.mellow.application.jpaservice.service.helper.Authentication.generateSalt;
import static com.mellow.application.jpaservice.service.helper.Authentication.hashPassword;
import static com.mellow.application.jpaservice.service.helper.Authentication.hashingIterations;
import static com.mellow.application.jpaservice.service.helper.Authentication.passwordMatches;
import static com.mellow.application.jpaservice.service.helper.Authentication.validateToken;

@Service
public class AuthenticationService {

    private UserService userService;
    private PropertyReader propertyReader;
    private final String issuer = "https://stormpath.com/";

    @Autowired
    public AuthenticationService(UserService userService) {
        this.userService = userService;
        this.propertyReader = new PropertyReader("config.properties");
    }

    public User createUser(Credentials credentials) {
        checkCredentialsPresence(credentials);
        String salt = generateSalt();
        String hashedPassword = hashPassword(credentials.getPassword(), salt);
        return userService.create(new User(credentials.getUsername(), hashedPassword, salt, hashingIterations));
    }

    public User authenticateUser(Credentials credentials) {
        checkCredentialsPresence(credentials);
        User user = userService.getByUsername(credentials.getUsername());
        if (passwordMatches(credentials.getPassword(), user.getSalt(), user.getPassword())) {
            return user;
        }
        throw new UnAuthorizedException("Wrong password");
    }

    public void validateAccessToken(String token) {
        validateToken(token, "Access Token", issuer, propertyReader.getJwtAccessSecretValue());
    }

    public void validateRefreshToken(String token) {
        validateToken(token, "Refresh Token", issuer, propertyReader.getJwtRefreshSecretValue());
    }

    public String getAccessToken() {
        return createAccessToken("Access Token", DateUtils.addHours(new Date(), 2), propertyReader.getJwtAccessSecretValue());
    }

    public String getRefreshToken() {
        return createRefreshToken("Refresh Token", propertyReader.getJwtRefreshSecretValue());
    }
}