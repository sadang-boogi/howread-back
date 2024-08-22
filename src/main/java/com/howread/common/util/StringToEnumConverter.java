package com.howread.common.util;

import com.howread.common.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

    private final Class<T> enumType;

    public StringToEnumConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T convert(String source) {
        try {
            return Enum.valueOf(enumType, source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("잘못된 요청입니다.", "유효하지 않은 " + enumType.getSimpleName() + "입니다.");
        }
    }
}

