package com.rebook.user.util;

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
        throw new IllegalArgumentException("지원 하지 않는 소셜 로그인입니다 : " + value);
    }
}
