package com.rebook.user.config;

import com.rebook.user.util.StringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final StringToEnumConverter stringToEnumConverter;

    public WebConfig(StringToEnumConverter stringToEnumConverter) {
        this.stringToEnumConverter = stringToEnumConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToEnumConverter);
    }
}