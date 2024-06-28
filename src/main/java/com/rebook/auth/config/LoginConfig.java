package com.rebook.auth.config;

import com.rebook.auth.interceptor.LoginCheckInterceptor;
import com.rebook.auth.resolver.AuthenticatedArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    private final AuthenticatedArgumentResolver authenticatedArgumentResolver;
    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor);
    }
}
