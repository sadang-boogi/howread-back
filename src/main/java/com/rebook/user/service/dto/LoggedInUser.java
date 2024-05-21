package com.rebook.user.service.dto;

import com.rebook.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LoggedInUser {

    private Long userId;
    private String email;
    private String name;

    public static LoggedInUser fromEntity(UserEntity userEntity) {
        return LoggedInUser.builder()
                .userId(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getNickname())
                .build();
    }
}
