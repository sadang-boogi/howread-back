package com.howread.hashtag.controller.requeest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class HashtagRequest {

    @JsonProperty("name")
    @NotBlank(message = "해시태그 이름을 입력해주세요.")
    private final String name;

    @JsonCreator
    public HashtagRequest(@JsonProperty("name") String name) {
        this.name = name;
    }
}
