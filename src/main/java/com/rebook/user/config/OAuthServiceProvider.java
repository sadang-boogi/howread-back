package com.rebook.user.config;

import com.rebook.user.util.GoogleOAuthService;
import com.rebook.user.util.OAuthService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthServiceProvider {
    private static final Map<String, Class<? extends OAuthService>> providerMap = new HashMap<>();
    private final ApplicationContext applicationContext;

    public OAuthServiceProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        providerMap.put("google", GoogleOAuthService.class);
        // 나중에 다른 소셜 로그인 추가 가능 providerMap.put("naver", NaverOAuthService.class);
    }

    public OAuthService getService(String provider) {
        Class<? extends OAuthService> serviceClass = providerMap.get(provider.toLowerCase());
        if (serviceClass != null) {
            return applicationContext.getBean(serviceClass);
        }
        throw new IllegalArgumentException("No proper service for the given provider: " + provider);
    }
}