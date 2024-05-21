package com.rebook.common.exception;

import com.rebook.user.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.error("errorMessage: {}", e.getMessage());

        String errorMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();

        ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), "BAD_REQUEST", errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Object> handleUnauthorizedException(TokenException e) {
        log.error("errorMessage: {}", e.getMessage());

        AuthExceptionResponse exception = new AuthExceptionResponse(LocalDateTime.now(), e.getCode(), e.getTitle(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(LocalDateTime.now(), e.getCode(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}
