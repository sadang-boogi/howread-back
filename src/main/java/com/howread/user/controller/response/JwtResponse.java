package com.howread.user.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String accessToken;
    private String refreshToken;
}
