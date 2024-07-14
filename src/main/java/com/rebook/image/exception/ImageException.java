package com.rebook.image.exception;

import com.rebook.common.exception.BaseException;
import com.rebook.common.exception.ExceptionCode;
import lombok.Getter;

@Getter
public class ImageException extends BaseException {

    public ImageException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public ImageException(String code, String title, String message) {
        super(code, title, message);
    }
}
