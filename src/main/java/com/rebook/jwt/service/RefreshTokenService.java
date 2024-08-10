package com.rebook.jwt.service;

import com.rebook.jwt.domain.RefreshTokenEntity;
import com.rebook.jwt.repository.RefreshTokenRepository;
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
