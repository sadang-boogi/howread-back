package com.rebook.user.util;

import com.rebook.common.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, SocialType> {
    @Override
    public SocialType convert(String type) {
        try {
            return SocialType.fromString(type);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("URL 반환 실패", "지원하지 않는 소셜 로그인 입니다");
        }
    }
}
