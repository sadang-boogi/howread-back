package com.rebook.common.exception;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends BaseException {
    public UnAuthorizedException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public UnAuthorizedException(String title, String message) {
        super("UN_AUTHORIZED", title, message);
    }
}
