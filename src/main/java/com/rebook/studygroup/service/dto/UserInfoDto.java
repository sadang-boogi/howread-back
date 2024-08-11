package com.rebook.studygroup.service.dto;

import com.rebook.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoDto {
    private Long userId;
    private String nickname;
    private String avatarUrl;

    public static UserInfoDto from(UserEntity userEntity) {
        return new UserInfoDto(
                userEntity.getId(),
                userEntity.getNickname(),
                userEntity.getAvatarUrl()
        );
    }
}
