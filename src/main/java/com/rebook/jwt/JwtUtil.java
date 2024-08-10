package com.rebook.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.jwt.dto.JwtPayload;
import com.rebook.jwt.service.JwtProperties;
import com.rebook.user.service.dto.AuthClaims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final static String TOKEN_PREFIX = "Bearer ";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    public String extractTokenFromHeader(String header) {
        return header.replace(TOKEN_PREFIX, "");
    }

    public String createAccessToken(AuthClaims authClaims) {
        Long validityInMilliSecond = jwtProperties.getAccessTokenValidityInSeconds();
        return createToken(authClaims, validityInMilliSecond);
    }

    public String createRefreshToken(AuthClaims authClaims) {
        Long validityInMilliSecond = jwtProperties.getRefreshTokenValidityInSeconds();
        return createToken(authClaims, validityInMilliSecond);
    }

    private String createToken(final AuthClaims authClaims, final long validityInMilliseconds) {
        String subject = String.valueOf(authClaims.getUserId());
        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + validityInMilliseconds);
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .signWith(secretKey, SIGNATURE_ALGORITHM)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        Instant expiredAt = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret()))
                .build().verify(token)
                .getExpiresAtAsInstant();
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
        String payload = JWT.decode(token)
                .getPayload();

        byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
        String decodedPayload = new String(decodedBytes);

        return parseUserFromJwt(decodedPayload);
    }

    private AuthClaims parseUserFromJwt(String decodedPayload) {
        try {
            JwtPayload payload = objectMapper.readValue(decodedPayload, JwtPayload.class);
            Long userId = payload.getSub();
            return new AuthClaims(userId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
