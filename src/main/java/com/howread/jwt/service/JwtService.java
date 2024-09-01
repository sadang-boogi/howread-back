package com.howread.jwt.service;

import com.howread.jwt.JwtUtil;
import com.howread.jwt.domain.RefreshTokenEntity;
import com.howread.jwt.repository.RefreshTokenRepository;
import com.howread.jwt.service.dto.RefreshAndAccessTokenDto;
import com.howread.user.exception.TokenException;
import com.howread.user.service.dto.AuthClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthClaims getClaims(String token) {
        validateToken(token);
        return jwtUtil.extractClaimFromToken(token);
    }

    public String extractTokenFromHeader(String header) {
        return jwtUtil.extractTokenFromHeader(header);
    }

    public RefreshAndAccessTokenDto renewalRefreshAndAccessToken(String refreshToken) {
        validateRefreshToken(refreshToken);
        AuthClaims authClaims = jwtUtil.extractClaimFromToken(refreshToken);

        String newAccessToken = jwtUtil.createAccessToken(authClaims);
        String newRefreshToken = jwtUtil.createRefreshToken(authClaims);

        saveRefreshToken(newRefreshToken, authClaims.getUserId());

        return new RefreshAndAccessTokenDto(newAccessToken, newRefreshToken);
    }

    public void saveRefreshToken(String refreshToken, Long userId) {
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .userId(userId)
                .token(refreshToken)
                .issuedAt(ZonedDateTime.now())
                .build();

        refreshTokenRepository.save(token);
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

        if (!refreshTokenRepository.existsByToken(refreshToken)) {
            throw new TokenException("INVALID_TOKEN", "리프레시 토큰이 유효하지 않습니다.", "다시 로그인 해주세요.");
        }
    }
}
