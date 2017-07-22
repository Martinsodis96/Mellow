package com.mellow.application.jpaservice.service;

import com.mellow.application.jpaservice.config.ConfigHelper;
import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.entity.model.UserModel;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.exception.DatabaseException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.NoSearchResultException;
import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.mellow.application.jpaservice.service.AuthenticationHelper.generateSalt;
import static com.mellow.application.jpaservice.service.AuthenticationHelper.hashPassword;
import static com.mellow.application.jpaservice.service.AuthenticationHelper.hashingIterations;

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
        validateCredentials(credentials);
        String salt = generateSalt();
        String hashedPassword = hashPassword(credentials.getPassword(), salt);
        return execute(userRepository1 -> userRepository.save(new UserModel(credentials.getUsername(),
                        hashedPassword, salt, hashingIterations)),
                String.format("Failed to create User with username: %s", credentials.getUsername()));
    }

    public UserModel authenticateUser(Credentials credentials) {
        checkCredentialsPresence(credentials);
        if(Optional.ofNullable(credentials.getUsername()).isPresent() && Optional.ofNullable(credentials.getPassword()).isPresent()){
            if (usernameExist(credentials.getUsername())) {
                UserModel user = userRepository.findByUsername(credentials.getUsername());
                if (passwordMatches(credentials.getPassword(), user.getSalt(), user.getPassword())) {
                    return user;
                }
                throw new UnAuthorizedException("Wrong password");
            }
            throw new NoSearchResultException(String.format("Could not find user with username %s", credentials.getUsername()));
        }
        throw new NoSearchResultException("Username or password can't be null");
    }

    public void validateAccessToken(String token) {
        validateToken(token, "Access Token", issuer, configHelper.getJwtAccessSecretValue());
    }

    public void validateRefreshToken(String token) {
        validateToken(token, "Refresh Token", issuer, configHelper.getJwtRefreshSecretValue());
    }

    public String createAccessToken() {
        return createToken("Access Token", DateUtils.addHours(new Date(), 2), configHelper.getJwtAccessSecretValue());
    }

    public String createRefreshToken() {
        return createToken("Refresh Token", DateUtils.addDays(new Date(), 12), configHelper.getJwtRefreshSecretValue());
    }

    private void validateToken(String token, String subject, String issuer, String secret) {
        if (Optional.ofNullable(token).isPresent()) {
            try {
                Jwts.parser()
                        .requireIssuer(issuer)
                        .requireSubject(subject)
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
            } catch (SignatureException e) {
                throw new UnAuthorizedException(String.format("Invalid %s", subject));
            } catch (ExpiredJwtException e) {
                throw new UnAuthorizedException(String.format("%s expired", subject));
            }
        } else {
            throw new UnAuthorizedException("Authorization header must be provided");
        }
    }

    private String createToken(String subject, Date expirationDate, String secret) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("https://stormpath.com/")
                .setExpiration(expirationDate)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private boolean passwordMatches(String password, String salt, String storedPassword) {
        return storedPassword.equals(hashPassword(password, salt));
    }

    private void validateCredentials(Credentials credentials) {
        checkCredentialsPresence(credentials);
        if (credentials.getUsername().length() < 3)
            throw new InvalidInputException("Username has to be at least 3 characters long");
        else if (usernameExist(credentials.getUsername()))
            throw new InvalidInputException("Username is already taken");
    }

    private void checkCredentialsPresence(Credentials credentials) {
        if(credentials != null){
            if (!Optional.ofNullable(credentials.getUsername()).isPresent())
                throw new InvalidInputException("Missing username in body");
            else if (!Optional.ofNullable(credentials.getPassword()).isPresent())
                throw new InvalidInputException("Missing password in body");
        }else{
            throw new InvalidInputException("Missing credentials in request body");
        }
    }

    private boolean usernameExist(String username) {
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