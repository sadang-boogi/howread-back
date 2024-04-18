package com.rebook.hashtag.exception;

import com.rebook.common.exception.CustomNotFoundException;

public class HashtagNotFoundException extends CustomNotFoundException {
    public HashtagNotFoundException() {
        super("요청하신 해쉬태그는 존재하지 않습니다.");
    }
}
