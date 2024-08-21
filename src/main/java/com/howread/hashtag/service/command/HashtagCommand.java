package com.howread.hashtag.service.command;

import com.howread.hashtag.controller.requeest.HashtagRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HashtagCommand {

    private String name;

    @Builder
    private HashtagCommand(String name) {
        this.name = name;
    }

    public static HashtagCommand of(HashtagRequest request) {
        return HashtagCommand.builder()
                .name(request.getName())
                .build();
    }
}
