package com.rebook.user.service.command;

import com.rebook.user.controller.request.UserUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateCommand {

    private Long userId;
    private String nickname;

    public static UserUpdateCommand from(UserUpdateRequest request, Long userId) {
        return new UserUpdateCommand(
                userId,
                request.getNickname()
        );
    }
}
