package com.rebook.user.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    @NotNull(message = "닉네임을 등록해주세요.")
    private String nickname;

    private String avatarUrl;

}
