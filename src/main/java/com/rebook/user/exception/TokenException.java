package com.rebook.user.exception;

import com.rebook.common.exception.BaseException;
import com.rebook.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class TokenException extends BaseException {

    public TokenException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public TokenException(String code, String title, String message) {
        super(code, title, message);
    }
}
