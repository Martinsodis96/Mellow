package com.mellow.application.jpaservice.service.implementation;

import com.mellow.application.jpaservice.config.ConfigHelper;
import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.entity.User;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.mellow.application.jpaservice.service.helper.Authentication.checkCredentialsPresence;
import static com.mellow.application.jpaservice.service.helper.Authentication.createAccessToken;
import static com.mellow.application.jpaservice.service.helper.Authentication.createRefreshToken;
import static com.mellow.application.jpaservice.service.helper.Authentication.passwordMatches;
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

    public User authenticateUser(Credentials credentials) {
        checkCredentialsPresence(credentials);
        Optional<User> optionalUser = userRepository.findByUsername(credentials.getUsername());
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
}