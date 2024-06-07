package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_BOOK_ID("NOT_FOUND", null, "요청하신 책은 존재하지 않습니다."),
    NOT_FOUND_HASHTAG_ID("NOT_FOUND", null, "요청하신 해쉬태그는 존재하지 않습니다."),
    TOKEN_MISSING("TOKEN_MISSING", "로그인에 실패하였습니다.", "인증 토큰이 누락되었습니다. 다시 로그인 해주세요."),
    TOKEN_INVALID("TOKEN_INVALID", "로그인에 실패하였습니다.", "인증 토큰이 유효하지 않습니다. 다시 로그인 해주세요.");;

    private final String code;
    private final String title;
    private final String message;
}
