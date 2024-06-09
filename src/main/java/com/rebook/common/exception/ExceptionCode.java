package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_BOOK_ID("NOT_FOUND", null, "요청하신 책은 존재하지 않습니다."),
    NOT_FOUND_HASHTAG_ID("NOT_FOUND", null, "요청하신 해쉬태그는 존재하지 않습니다."),
    TOKEN_INVALID("TOKEN_ERROR", "로그인 실패", "로그인에 실패했습니다. 다시 로그인 해주세요."),
    LOGIN_REQUIRED("LOGIN_REQUIRED", "로그인 필요", "이 기능을 이용하려면 로그인이 필요합니다.");


    private final String code;
    private final String title;
    private final String message;
}
