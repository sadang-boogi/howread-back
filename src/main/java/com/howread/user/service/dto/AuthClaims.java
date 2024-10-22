package com.howread.user.service.dto;

import com.howread.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AuthClaims {

    private Long userId;

    public static AuthClaims fromEntity(UserEntity userEntity) {
        return AuthClaims.builder()
                .userId(userEntity.getId())
                .build();
    }
}
