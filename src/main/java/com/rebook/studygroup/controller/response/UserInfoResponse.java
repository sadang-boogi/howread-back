package com.rebook.studygroup.controller.response;

import com.rebook.studygroup.service.dto.UserInfoDto;
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
