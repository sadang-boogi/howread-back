package com.howread.studygroup.controller.response;

import com.howread.studygroup.service.dto.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoResponse {
    private Long id;
    private String nickname;
    private String avatarUrl;

    public static UserInfoResponse from(UserInfoDto userInfoDto) {
        return new UserInfoResponse(
                userInfoDto.getId(),
                userInfoDto.getNickname(),
                userInfoDto.getAvatarUrl()
        );
    }
}
