package com.rebook.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TokenExceptionCode {

    TOKEN_MISSING("TOKEN_MISSING", "로그인에 실패하였습니다.", "인증 토큰이 누락되었습니다. 다시 로그인 해주세요."),
    TOKEN_INVALID("TOKEN_INVALID", "로그인에 실패하였습니다.", "인증 토큰이 유효하지 않습니다. 다시 로그인 해주세요.");

    private final String code;
    private final String title;
    private final String message;


}
