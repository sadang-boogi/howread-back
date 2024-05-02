package com.rebook.hashtag.controller.response;

import com.rebook.hashtag.service.dto.HashtagDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagResponse {

    private Long id;
    private String name;

    @Builder
    private HashtagResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HashtagResponse fromDto(HashtagDto hashtagDto) {
        return HashtagResponse.builder()
                .id(hashtagDto.getId())
                .name(hashtagDto.getName())
                .build();
    }
}
