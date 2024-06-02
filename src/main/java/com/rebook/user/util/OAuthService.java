package com.rebook.user.util;

import com.rebook.user.service.dto.UserCommand;

public interface OAuthService {
    String getAuthorizationUrl();

    String getAccessToken(String authorizationCode);

    UserCommand getUserProfile(String accessToken);
}
