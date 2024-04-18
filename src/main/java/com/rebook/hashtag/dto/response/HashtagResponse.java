package com.rebook.hashtag.dto.response;

import com.rebook.hashtag.domain.HashtagEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HashtagResponse {

    private final Long id;
    private final String name;

    public static HashtagResponse of(HashtagEntity hashtagEntity) {
        return new HashtagResponse(
                hashtagEntity.getId(),
                hashtagEntity.getName()
        );
    }
}
