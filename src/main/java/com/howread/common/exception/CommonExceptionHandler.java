package com.howread.common.exception;

import com.howread.user.exception.TokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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

        ExceptionResponse exception = new ExceptionResponse("BAD_REQUEST", "잘못된 요청입니다.", errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(e.getCode(), e.getTitle(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(TokenException e) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(e.getCode(), e.getTitle(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(e.getCode(), e.getTitle(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(BadRequestException e, WebRequest request) {
        log.error("errorMessage: {}", e.getMessage());

        ExceptionResponse exception = new ExceptionResponse(e.getCode(), e.getTitle(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }
}