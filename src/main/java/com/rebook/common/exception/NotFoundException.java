package com.rebook.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends BaseException {

    public NotFoundException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public NotFoundException(String title, String message) {
        super("NOT_FOUND", title, message);
    }


}
