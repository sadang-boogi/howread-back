package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_HASHTAG_ID(HttpStatus.BAD_REQUEST.value(), "요청하신 해쉬태그는 존재하지 않습니다.");

    private final int errorCode;
    private final String message;
}
