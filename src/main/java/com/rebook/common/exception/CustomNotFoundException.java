package com.rebook.common.exception;

public abstract class CustomNotFoundException extends RuntimeException {

    public CustomNotFoundException(String message) {
        super(message);
    }
}
