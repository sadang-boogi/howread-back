package com.rebook.user.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String type) {
        return SocialType.fromString(type);
    }
}