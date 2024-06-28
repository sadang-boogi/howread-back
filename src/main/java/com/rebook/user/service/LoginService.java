package com.rebook.user.service;

import com.rebook.user.config.OAuthServiceProvider;
import com.rebook.user.service.dto.AuthClaims;
import com.rebook.user.service.dto.UserCommand;
import com.rebook.user.util.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserService userService;
    private final OAuthServiceProvider oAuthServiceProvider;

    public AuthClaims socialLogin(String code, String registrationId) {

        OAuthService oauthProvider = oAuthServiceProvider.getService(registrationId.toLowerCase());
        if (oauthProvider == null) {
            throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        }
        String accessToken = oauthProvider.getAccessToken(code);
        UserCommand userCommand = oauthProvider.getUserProfile(accessToken);
        return userService.createUser(userCommand);
    }

    public String getAuthorizationUrl(String registrationId) {
        OAuthService oauthProvider = oAuthServiceProvider.getService(registrationId.toLowerCase());
        if (oauthProvider == null) {
            throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        }
        return oauthProvider.getAuthorizationUrl();
    }

}