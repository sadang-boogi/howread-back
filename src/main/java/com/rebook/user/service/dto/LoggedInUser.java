package com.rebook.user.service.dto;

import com.rebook.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
@Getter
@Builder
public class LoggedInUser {

    private String email;
    private String name;

    public static LoggedInUser fromEntity(UserEntity userEntity) {
        return LoggedInUser.builder()
                .email(userEntity.getEmail())
                .name(userEntity.getEmail())
                .build();
    }
}
