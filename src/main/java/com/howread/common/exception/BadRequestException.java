package com.howread.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends BaseException {

    public BadRequestException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public BadRequestException(String title, String message) {
        super("BAD_REQUEST", title, message);
    }
}
