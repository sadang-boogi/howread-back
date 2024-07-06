package com.rebook.user.service.command;

import com.rebook.user.controller.request.UserUpdateRequest;
import com.rebook.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateCommand {

    private Long userId;
    private String nickname;
    private String email;
    private Role role;

    public static UserUpdateCommand from(UserUpdateRequest request, Long userId) {
        return new UserUpdateCommand(
                userId,
                request.getNickname(),
                request.getEmail(),
                request.getRole()
        );
    }
}
