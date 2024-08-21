package com.howread.hashtag.service.dto;

import com.howread.hashtag.domain.HashtagEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HashtagDto {
    private Long id;
    private String name;

    @Builder
    private HashtagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static HashtagDto fromEntity(HashtagEntity hashtagEntity) {
        return HashtagDto.builder()
                .id(hashtagEntity.getId())
                .name(hashtagEntity.getName())
                .build();
    }
}
