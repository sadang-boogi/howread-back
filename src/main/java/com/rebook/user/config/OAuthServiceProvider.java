package com.rebook.user.config;

import com.rebook.user.util.OAuthService;
import com.rebook.user.util.SocialType;
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
        // Enum을 이용해 SocialType과 OAuthService 클래스를 매핑
        for (SocialType type : SocialType.values()) {
            providerMap.put(type.name().toLowerCase(), type.getClazz());
        }
    }

    public OAuthService getService(SocialType type) {
        Class<? extends OAuthService> serviceClass = providerMap.get(type.name().toLowerCase());
        if (serviceClass != null) {
            return applicationContext.getBean(serviceClass);
        }
        throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다. " + type.name());
    }
}