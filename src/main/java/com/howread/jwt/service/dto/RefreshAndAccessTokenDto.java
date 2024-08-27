package com.howread.jwt.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshAndAccessTokenDto {
    private String accessToken;
    private String refreshToken;
}
