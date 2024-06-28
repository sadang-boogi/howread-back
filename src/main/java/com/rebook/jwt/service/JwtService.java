package com.rebook.jwt.service;

import com.rebook.jwt.JwtUtil;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.exception.TokenException;
import com.rebook.user.repository.UserRepository;
import com.rebook.user.service.dto.AuthClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtUtil jwtUtil;

    public AuthClaims getClaims(String token) {
        validateToken(token);
        return jwtUtil.extractClaimFromToken(token);
    }

    public String extractTokenFromHeader(String header) {
        return jwtUtil.extractTokenFromHeader(header);
    }

    private void validateToken(String token) {
        if (!jwtUtil.isTokenNotManipulated(token)) {
            throw new TokenException("INVALID_TOKEN", "로그인 실패", "다시 로그인 해주세요.");
        }

        if (jwtUtil.isTokenExpired(token)) {
            throw new TokenException("TOKEN_EXPIRED", "로그인 필요", "세션이 만료되었으므로 다시 로그인 해주세요.");
        }
    }
}
