package com.howread.user.util;

import com.howread.user.service.dto.UserCommand;

public interface OAuthService {
    String getAuthorizationUrl(String redirectUri);

    String getAccessToken(String authorizationCode, String redirectUri);

    UserCommand getUserProfile(String accessToken);
}
