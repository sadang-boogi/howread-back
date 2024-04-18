package com.rebook.hashtag.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HashtagCreateRequest {

    @NotBlank(message = "해시태그 이름을 입력해주세요.")
    private final String name;

}
