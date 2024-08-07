package com.rebook.common.config;

import com.rebook.reaction.util.StringToReactionTypeConverter;
import com.rebook.reaction.util.StringToTargetTypeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final StringToReactionTypeConverter stringToReactionTypeConverter;
    private final StringToTargetTypeConverter stringToTargetTypeConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToReactionTypeConverter);
        registry.addConverter(stringToTargetTypeConverter);
    }
}
