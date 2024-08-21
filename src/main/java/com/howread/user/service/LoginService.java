package com.howread.user.service;

import com.howread.user.config.OAuthServiceProvider;
import com.howread.user.service.dto.AuthClaims;
import com.howread.user.service.dto.UserCommand;
import com.howread.user.util.OAuthService;
import com.howread.user.util.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserService userService;
    private final OAuthServiceProvider oAuthServiceProvider;

    public AuthClaims socialLogin(String code, SocialType type, String redirectUri) {
        OAuthService oAuthService = oAuthServiceProvider.getService(type);

        if (oAuthService == null) {
            throw new IllegalArgumentException("Unsupported registrationId: " + type.name());
        }

        String accessToken = oAuthService.getAccessToken(code, redirectUri);
        UserCommand userCommand = oAuthService.getUserProfile(accessToken);

        return userService.createUser(userCommand);
    }

    public String getAuthorizationUrl(SocialType type, String redirectUri) {
        OAuthService oAuthService = oAuthServiceProvider.getService(type);

        if (oAuthService == null) {
            throw new IllegalArgumentException("Unsupported registrationId: " + type.name());
        }

        return oAuthService.getAuthorizationUrl(redirectUri);
    }
}
