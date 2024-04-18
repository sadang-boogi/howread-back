package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {

    private final LocalDateTime errorTime;
    private final int errorCode;
    private final String message;
}
