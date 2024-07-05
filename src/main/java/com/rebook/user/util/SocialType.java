package com.rebook.user.util;

import com.rebook.common.exception.BadRequestException;
import lombok.Getter;

public enum SocialType {
    GOOGLE(GoogleOAuthService.class);

    @Getter
    private final Class<? extends OAuthService> clazz;

    SocialType(Class<? extends OAuthService> clazz) {
        this.clazz = clazz;
    }

    public static SocialType fromString(String value) {
        for (SocialType type : SocialType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new BadRequestException("URL 반환 실패", "지원하지 않는 소셜 로그인 입니다");
    }
}
