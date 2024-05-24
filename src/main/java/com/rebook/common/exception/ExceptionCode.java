package com.rebook.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    NOT_FOUND_BOOK_ID("NOT_FOUND", null, "요청하신 책은 존재하지 않습니다."),
    NOT_FOUND_REVIEW_UPDATE("NOT_FOUND", "리뷰 수정에 실패하였습니다.", "요청하신 리뷰가 존재하지 않습니다."),
    NOT_FOUND_REVIEW_DELETE("NOT_FOUND", "리뷰 삭제에 실패하였습니다.", "요청하신 리뷰가 존재하지 않습니다."),
    NOT_FOUND_HASHTAG_ID("NOT_FOUND", null, "요청하신 해쉬태그는 존재하지 않습니다."),
    TOKEN_MISSING("TOKEN_MISSING", "로그인에 실패하였습니다.", "토큰이 존재하지 않습니다."),
    ;

    private final String code;
    private final String title;
    private final String message;
}
