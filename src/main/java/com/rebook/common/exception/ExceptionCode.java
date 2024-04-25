package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_BOOK_ID("NOT_FOUND", "요청하신 책은 존재하지 않습니다."),
    NOT_FOUND_HASHTAG_ID("NOT_FOUND", "요청하신 해쉬태그는 존재하지 않습니다."),
    ;

    private final String code;
    private final String message;
}
