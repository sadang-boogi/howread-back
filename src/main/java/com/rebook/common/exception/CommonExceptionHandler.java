package com.rebook.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        log.error("errorCode: {}, errorMessage: {}", e.getErrorCode(), e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), e.getErrorCode(), e.getMessage());

        return ResponseEntity.badRequest().body(exception);
    }
}
