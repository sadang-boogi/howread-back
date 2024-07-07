package com.rebook.user.service.dto;

import com.rebook.user.domain.Role;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.util.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String nickname;
    private String email;
    private Role role;
    private SocialType socialType;
    private String socialId;

    public static UserDto fromEntity(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getNickname(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getSocialType(),
                userEntity.getSocialId()
        );
    }
}