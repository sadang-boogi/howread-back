package com.rebook.user.util;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleOAuthProperties {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String endPoint;
    private final String responseType;
    private final String scopes;
    private final String tokenUri;
    private final String userInfoUri;

    public GoogleOAuthProperties(Environment environment) {
        this.clientId = environment.getProperty("oauth.google.client-id");
        this.clientSecret = environment.getProperty("oauth.google.client-secret");
        this.redirectUri = environment.getProperty("oauth.google.redirect-uri");
        this.endPoint = environment.getProperty("oauth.google.end-point");
        this.responseType = environment.getProperty("oauth.google.response-type");
        this.scopes = environment.getProperty("oauth.google.scopes");
        this.tokenUri = environment.getProperty("oauth.google.token-uri");
        this.userInfoUri = environment.getProperty("oauth.google.user-info-uri");
    }
}