package com.rebook.user.exception;

import com.rebook.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final String code;
    private final String title;
    private final String message;

    public TokenException(TokenExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.title = exceptionCode.getTitle();
        this.message = exceptionCode.getMessage();
    }
}
