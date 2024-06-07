package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String code;
    private final String title;
    private final String message;
}
