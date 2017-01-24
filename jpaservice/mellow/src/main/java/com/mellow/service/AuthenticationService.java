package com.mellow.service;

import com.mellow.config.ConfigHelper;
import com.mellow.model.Credentials;
import com.mellow.model.UserModel;
import com.mellow.repository.UserRepository;
import com.mellow.service.exception.DatabaseException;
import com.mellow.service.exception.InvalidInputException;
import com.mellow.service.exception.NoSearchResultException;
import com.mellow.service.exception.UnAuthorizedException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import static com.mellow.service.SecurityHelper.generateSalt;
import static com.mellow.service.SecurityHelper.hashPassword;
import static com.mellow.service.SecurityHelper.hashingIterations;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private ConfigHelper configHelper;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.configHelper = new ConfigHelper("config.properties");
    }

    public String createUser(Credentials credentials) {
        validateCredentials(credentials);
        String salt = generateSalt();
        String hashedPassword = hashPassword(credentials.getPassword(), salt);
        execute(userRepository1 -> userRepository.save(new UserModel(credentials.getUsername(),
                        hashedPassword, salt, hashingIterations)),
                String.format("Failed to create User with username: %s", credentials.getUsername()));

        return createJwtToken(credentials.getUsername());
    }

    public String authenticateUser(Credentials credentials){
        checkCredentialsPresence(credentials);
        if(usernameExist(credentials.getUsername())){
            UserModel user = userRepository.findByUsername(credentials.getUsername());
            if(passwordMatches(credentials.getPassword(), user.getSalt(), user.getPassword())){
                return createJwtToken(credentials.getUsername());
            }
            throw new UnAuthorizedException("Wrong password");
        }
            throw new NoSearchResultException(String.format("Could not find user with username %s", credentials.getUsername()));
    }

    public void validateToken(String token){
        if(token != null) {
            try{
                Jwts.parser()
                    .setSigningKey(configHelper.getJwtSecretValue())
                    .parseClaimsJws(token)
                    .getBody();
            }catch (SignatureException e) {
                throw new UnAuthorizedException();
            }
        }else{
            throw new NoSearchResultException("?");
        }
    }

    //TODO how to generate/store a key
    private String createJwtToken(String username){
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("jjwt")
                .setExpiration(DateUtils.addHours(new Date(), 2))
                .setSubject(username)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, configHelper.getJwtSecretValue())
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

    private void checkCredentialsPresence(Credentials credentials){
        if (!Optional.ofNullable(credentials.getUsername()).isPresent())
            throw new InvalidInputException("Missing username in body");
        else if (!Optional.ofNullable(credentials.getPassword()).isPresent())
            throw new InvalidInputException("Missing password in body");
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