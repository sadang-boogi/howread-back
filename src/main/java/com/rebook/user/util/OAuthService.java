package com.rebook.user.util;

import com.rebook.user.service.dto.UserCommand;

public interface OAuthService {
    String getAuthorizationUrl(String redirectUri);

    String getAccessToken(String authorizationCode, String redirectUri);

    UserCommand getUserProfile(String accessToken);
}
