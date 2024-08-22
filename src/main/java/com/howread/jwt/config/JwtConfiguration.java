package com.howread.jwt.config;

import com.howread.jwt.service.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {
    private final JwtProperties jwtProperties;
}
