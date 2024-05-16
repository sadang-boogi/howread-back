package com.rebook.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;

import com.rebook.user.service.dto.LoggedInUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtil {
    @Value("${jwt.secret-key}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    private int expirationTimeMillis;
    private String tokenPrefix = "Bearer ";
    private ObjectMapper objectMapper = new ObjectMapper();

    public boolean isIncludeTokenPrefix(String header) {
        return header.split(" ")[0].equals(tokenPrefix.trim());
    }

    public String extractTokenFromHeader(String header) {
        return header.replace(tokenPrefix, "");
    }

    public String createToken(LoggedInUser loggedInUser, Instant currentDate) {
        return JWT.create()
                .withSubject(String.valueOf(loggedInUser.getUserId()))
                .withExpiresAt(currentDate.plusMillis(expirationTimeMillis))
                .withClaim("email", loggedInUser.getEmail())
                .withClaim("username", loggedInUser.getName())
                .sign(Algorithm.HMAC512(secret));
    }

    public boolean isTokenExpired(String token) {
        Instant expiredAt = JWT.require(Algorithm.HMAC512(secret))
                .build().verify(token)
                .getExpiresAtAsInstant();

        return expiredAt.isBefore(Instant.now());
    }

    public boolean isTokenNotManipulated(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build().verify(token)
                .getSignature()
                .equals(secret);
    }

    public LoggedInUser extractUserFromToken(String token) {
        String payload = JWT.decode(token)
                .getPayload();

        byte[] decodedBytes = Base64.getDecoder().decode(payload);
        String decodedPayload = new String(decodedBytes);

        return parseUserFromJwt(decodedPayload);
    }

    private LoggedInUser parseUserFromJwt(String decodedPayload) {
        try {
            LinkedHashMap<String, Object> payloadMap = objectMapper.readValue(decodedPayload, LinkedHashMap.class);
            Long userId = Long.parseLong((String) payloadMap.get("sub"));
            String email = (String) payloadMap.get("email");
            String username = (String) payloadMap.get("username");
            return new LoggedInUser(userId, email, username);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}