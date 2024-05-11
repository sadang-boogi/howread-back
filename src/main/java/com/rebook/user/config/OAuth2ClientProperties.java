package com.rebook.user.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Map;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class OAuth2ClientProperties {

    private final Map<String, Registration> registration;
    private final Map<String, Provider> provider;

    @ConstructorBinding
    public OAuth2ClientProperties(Map<String, Registration> registration, Map<String, Provider> provider) {
        this.registration = registration;
        this.provider = provider;
    }

    public Map<String, Registration> getRegistration() {
        return registration;
    }

    public Map<String, Provider> getProvider() {
        return provider;
    }

    @Getter
    @AllArgsConstructor
    public static class Registration {
        private String clientId;
        private String clientSecret;
        private String redirectUri;

    }

    @Getter
    @AllArgsConstructor
    public static class Provider {
        private String tokenUri;
        private String userInfoUri;
    }


}