package com.rebook.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;
    private final String message;

    public NotFoundException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
