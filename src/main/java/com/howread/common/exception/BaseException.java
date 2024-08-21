package com.howread.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final String code;
    private final String title;
    private final String message;

    public BaseException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.title = exceptionCode.getTitle();
        this.message = exceptionCode.getMessage();
    }

    public BaseException(String code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }
}
