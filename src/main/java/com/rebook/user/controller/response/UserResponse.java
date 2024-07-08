package com.rebook.user.controller.response;

import com.rebook.user.service.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponse {
    private Long id;
    private String nickname;
    private String email;

    public static UserResponse fromDto(UserDto user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail()
        );
    }
}
