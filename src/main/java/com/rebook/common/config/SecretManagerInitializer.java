package com.rebook.common.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SecretManagerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private final SecretManagerService secretManagerService;

    public SecretManagerInitializer(SecretManagerService secretManagerService) {
        this.secretManagerService = secretManagerService;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        secretManagerService.setSecret();
    }
}