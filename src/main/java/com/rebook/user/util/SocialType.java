package com.rebook.user.util;

import lombok.Getter;

public enum SocialType {
    GOOGLE(GoogleOAuthService.class);

    @Getter
    private final Class<? extends OAuthService> clazz;

    SocialType(Class<? extends OAuthService> clazz) {
        this.clazz = clazz;
    }
}
