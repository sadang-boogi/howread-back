package com.rebook.user.controller.request;

import com.rebook.user.domain.Role;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

    private String nickname;
    private String email;
    private Role role;

}
