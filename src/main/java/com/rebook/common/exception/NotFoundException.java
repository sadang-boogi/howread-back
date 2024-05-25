package com.rebook.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;
    private final String title;
    private final String message;

    public NotFoundException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.title = exceptionCode.getTitle();
        this.message = exceptionCode.getMessage();
    }

    public NotFoundException(String title, String message) {
        this.code = "NOT_FOUND";
        this.title = title;
        this.message = message;
    }


}
