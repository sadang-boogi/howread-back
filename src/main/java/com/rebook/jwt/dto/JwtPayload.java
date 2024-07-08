package com.rebook.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtPayload {
    private Long sub;
    private Long exp;
}
