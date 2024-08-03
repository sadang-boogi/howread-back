package com.rebook.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.jwt.dto.JwtPayload;
import com.rebook.jwt.service.JwtProperties;
import com.rebook.user.service.dto.AuthClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final String tokenPrefix = "Bearer ";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean isIncludeTokenPrefix(String header) {
        return header.split(" ")[0].equals(tokenPrefix.trim());
    }

    public String extractTokenFromHeader(String header) {
        return header.replace(tokenPrefix, "");
    }

    public String createToken(AuthClaims authClaims, Instant currentDate) {
        return JWT.create()
                .withSubject(String.valueOf(authClaims.getUserId()))
                .withExpiresAt(currentDate.plusSeconds(jwtProperties.getTokenValidityInSeconds()))
                .sign(Algorithm.HMAC512(jwtProperties.getSecret()));
    }

    public boolean isTokenExpired(String token) {
        Instant expiredAt = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                .build().verify(token)
                .getExpiresAt().toInstant();
        return expiredAt.isBefore(Instant.now());
    }

    public boolean isTokenNotManipulated(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public AuthClaims extractClaimFromToken(String token) {
        String payload = JWT.decode(token).getPayload();

        // URL-safe Base64 디코딩
        byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);

        // UTF-8 인코딩을 사용하여 문자열로 변환
        String decodedPayload = new String(decodedBytes, StandardCharsets.UTF_8);

        return parseUserFromJwt(decodedPayload);
    }

    private AuthClaims parseUserFromJwt(String decodedPayload) {
        try {
            JwtPayload payload = objectMapper.readValue(decodedPayload, JwtPayload.class);
            Long userId = payload.getSub();
            return new AuthClaims(userId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JWT payload", e);
        }
    }
}
