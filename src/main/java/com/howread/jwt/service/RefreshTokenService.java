package com.howread.jwt.service;

import com.howread.jwt.domain.RefreshTokenEntity;
import com.howread.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String refreshToken, Long userId) {
        RefreshTokenEntity token = RefreshTokenEntity.builder()
                .token(refreshToken)
                .userId(userId)
                .build();

        refreshTokenRepository.save(token);
    }
}
