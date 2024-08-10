package com.rebook.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.jwt.dto.JwtPayload;
import com.rebook.jwt.service.JwtProperties;
import com.rebook.user.service.dto.AuthClaims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final static String TOKEN_PREFIX = "Bearer ";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

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
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        Date now = new Date();
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration()
                    .before(now);
        } catch (JwtException e) {
            return true;
        }
    }

    public boolean isTokenManipulated(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)  // 서명 검증을 위한 비밀키 설정
                    .build()
                    .parseClaimsJws(token);
            return false;
        } catch (JwtException e) {
            log.error(e.getMessage());
            return true;
        }
    }

    public AuthClaims extractClaimFromToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Long userId = Long.valueOf(claims.getSubject());
            return new AuthClaims(userId);
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
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
