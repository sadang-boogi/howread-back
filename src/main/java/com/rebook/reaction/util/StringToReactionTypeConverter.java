package com.rebook.reaction.util;

import com.rebook.common.exception.NotFoundException;
import com.rebook.reaction.domain.ReactionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToReactionTypeConverter implements Converter<String, ReactionType> {
    @Override
    public ReactionType convert(String source) {
        try {
            return ReactionType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("리액션 등록 실패","유효하지 않은 ReactionType입니다.");
        }
    }
}
