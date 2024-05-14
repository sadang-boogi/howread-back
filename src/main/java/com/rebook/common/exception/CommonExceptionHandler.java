package com.rebook.common.exception;

import com.rebook.user.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleBadRequestException(NotFoundException e) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<Object> handleUnauthorizedException(TokenException e) {
        log.error("errorMessage: {}", e.getMessage());

        AuthExceptionResponse exception = new AuthExceptionResponse(LocalDateTime.now(), e.getCode(), e.getTitle(), e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
    }
}
