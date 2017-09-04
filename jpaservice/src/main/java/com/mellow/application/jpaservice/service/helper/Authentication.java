package com.mellow.application.jpaservice.service.helper;

import com.mellow.application.jpaservice.entity.Credentials;
import com.mellow.application.jpaservice.entity.model.UserModel;
import com.mellow.application.jpaservice.repository.UserRepository;
import com.mellow.application.jpaservice.service.exception.HashingException;
import com.mellow.application.jpaservice.service.exception.InvalidInputException;
import com.mellow.application.jpaservice.service.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Optional;

public final class Authentication {

    public static final int hashingIterations = 100000;
    private static final int hashSize = 2048;

    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[hashSize / 8];
        secureRandom.nextBytes(salt);
        return new String(salt);
    }

    public static String hashPassword(final String password, final String salt) throws HashingException {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), hashingIterations, hashSize);
            SecretKey key = skf.generateSecret(spec);
            return new String(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HashingException(String.format("Cannot hash '%s'", password), e);
        }
    }

    public static boolean passwordMatches(String password, String salt, String storedPassword) {
        return storedPassword.equals(hashPassword(password, salt));
    }

    public static void validateToken(String token, String subject, String issuer, String secret) {
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

    public static String createAccessToken(String subject, Date expirationDate, String secret) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("https://stormpath.com/")
                .setExpiration(expirationDate)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static String createRefreshToken(String subject, String secret) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("https://stormpath.com/")
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static void validateCredentials(Credentials credentials, UserRepository userRepository) {
        if (credentials.getUsername().length() > 3) {
            Optional<UserModel> optionalUser = userRepository.findByUsername(credentials.getUsername());
            if (optionalUser.isPresent())
                throw new InvalidInputException("Username is already taken");
        } else
            throw new InvalidInputException("Username has to be at least 3 characters long");
    }

    public static void checkCredentialsPresence(Credentials credentials) {
        if (credentials != null) {
            if (!Optional.ofNullable(credentials.getUsername()).isPresent())
                throw new InvalidInputException("Missing username in body");
            else if (!Optional.ofNullable(credentials.getPassword()).isPresent())
                throw new InvalidInputException("Missing password in body");
        } else {
            throw new InvalidInputException("Missing credentials in request body");
        }
    }
}