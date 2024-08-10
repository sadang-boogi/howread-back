package com.rebook.jwt;

import com.rebook.jwt.service.JwtProperties;
import com.rebook.user.service.dto.AuthClaims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final JwtProperties jwtProperties;

    public String extractTokenFromHeader(String header) {
        return header.replace(TOKEN_PREFIX, "");
    }

    public String createAccessToken(AuthClaims authClaims) {
        log.info("AccessToken Validity: {}", jwtProperties.getAccessTokenValidityInSeconds());
        log.info("JWT Secret: {}", jwtProperties.getSecret());
        long validityInMilliSecond = jwtProperties.getAccessTokenValidityInSeconds();
        return createToken(authClaims, validityInMilliSecond);
    }

    public String createRefreshToken(AuthClaims authClaims) {
        long validityInMilliSecond = jwtProperties.getRefreshTokenValidityInSeconds();
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
                    .setSigningKey(secretKey)
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
}
