package com.rebook.user.service.dto;

import com.rebook.user.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserCommand {
    String name;
    String email;
    String socialId;
    String socialType;
}
