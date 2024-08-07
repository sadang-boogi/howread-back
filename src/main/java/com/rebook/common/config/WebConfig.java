package com.rebook.common.config;

import com.rebook.common.util.StringToEnumConverter;
import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public StringToEnumConverter<ReactionType> stringToReactionTypeConverter() {
        return new StringToEnumConverter<>(ReactionType.class);
    }

    @Bean
    public StringToEnumConverter<TargetType> stringToTargetTypeConverter() {
        return new StringToEnumConverter<>(TargetType.class);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToReactionTypeConverter());
        registry.addConverter(stringToTargetTypeConverter());
    }
}
