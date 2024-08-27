package com.howread.user.service.dto;

import com.howread.user.domain.Role;
import com.howread.user.domain.UserEntity;
import com.howread.user.util.SocialType;
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
    private String avatarUrl;

    public static UserDto fromEntity(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getNickname(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getSocialType(),
                userEntity.getSocialId(),
                userEntity.getAvatarUrl()
        );
    }
}
