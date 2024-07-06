package com.rebook.user.controller.response;

import com.rebook.user.domain.Role;
import com.rebook.user.service.dto.UserDto;
import com.rebook.user.util.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {
    private Long id;
    private String nickname;
    private String email;
    private Role role;
    private SocialType socialType;
    private String socialId;

    public static UserResponse fromDto(UserDto user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getSocialType(),
                user.getSocialId()
        );
    }
}
