package com.howread.jwt.service;

import com.howread.jwt.JwtUtil;
import com.howread.user.exception.TokenException;
import com.howread.user.service.dto.AuthClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthClaims getClaims(String token) {
        validateToken(token);
        return jwtUtil.extractClaimFromToken(token);
    }

    public String extractTokenFromHeader(String header) {
        return jwtUtil.extractTokenFromHeader(header);
    }

    public String refreshAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);
        AuthClaims authClaims = jwtUtil.extractClaimFromToken(refreshToken);
        return jwtUtil.createAccessToken(authClaims);
    }

    private void validateToken(String token) {
        if (jwtUtil.isTokenManipulated(token)) {
            throw new TokenException("INVALID_TOKEN", "로그인 실패", "다시 로그인 해주세요.");
        }

        if (jwtUtil.isTokenExpired(token)) {
            throw new TokenException("TOKEN_EXPIRED", "로그인 필요", "세션이 만료되었으므로 다시 로그인 해주세요.");
        }
    }

    private void validateRefreshToken(String refreshToken) {
        if (jwtUtil.isTokenManipulated(refreshToken)) {
            throw new TokenException("INVALID_TOKEN", "로그인 실패", "다시 로그인 해주세요.");
        }

        if (jwtUtil.isTokenExpired(refreshToken)) {
            throw new TokenException("TOKEN_EXPIRED", "리프레시 토큰이 만료되었습니다.", "세션이 만료되었으므로 다시 로그인 해주세요.");
        }

        if (!refreshTokenService.isValidRefreshToken(refreshToken)) {
            throw new TokenException("INVALID_TOKEN", "리프레시 토큰이 유효하지 않습니다.", "다시 로그인 해주세요.");
        }
    }
}
