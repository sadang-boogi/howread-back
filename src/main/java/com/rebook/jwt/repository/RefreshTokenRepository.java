package com.rebook.jwt.repository;

import com.rebook.jwt.domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    boolean existsByToken(String token);
}
