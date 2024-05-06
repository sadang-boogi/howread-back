package com.rebook.user.dto;

import com.rebook.user.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCommand {
    String name;
    String email;
    String role;
}
