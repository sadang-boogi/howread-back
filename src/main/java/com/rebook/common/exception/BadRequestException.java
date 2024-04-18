package com.rebook.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public BadRequestException(ExceptionCode exceptionCode) {
        this.errorCode = exceptionCode.getErrorCode();
        this.message = exceptionCode.getMessage();
    }
}
