package com.rebook.reaction.util;

import com.rebook.common.exception.BadRequestException;
import com.rebook.reaction.domain.TargetType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTargetTypeConverter implements Converter<String, TargetType> {
    @Override
    public TargetType convert(String source) {
        try {
            return TargetType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("리액션 등록 실패","유효하지 않은 ReactionType입니다.");
        }
    }
}
