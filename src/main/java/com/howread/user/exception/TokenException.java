package com.howread.user.exception;

import com.howread.common.exception.BaseException;
import com.howread.common.exception.ExceptionCode;
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
