package com.rebook.common.config;

import com.rebook.common.util.StringToEnumConverter;
import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
import com.rebook.user.util.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //제네릭 컨버터 사용시, 명시적으로 타입 표시
        registry.addConverter(String.class, TargetType.class, new StringToEnumConverter<>(TargetType.class));
        registry.addConverter(String.class, ReactionType.class, new StringToEnumConverter<>(ReactionType.class));
        registry.addConverter(String.class, SocialType.class, new StringToEnumConverter<>(SocialType.class));
    }
}
