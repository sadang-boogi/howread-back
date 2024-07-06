package com.rebook.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(0) // 제일 먼저 실행되도록
public class SecretManagerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final SecretManagerService secretManagerService;

    public SecretManagerInitializer(SecretManagerService secretManagerService) {
        this.secretManagerService = secretManagerService;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        secretManagerService.setSecret();
        log.info("Secret Manager Initialization Complete");
    }
}
