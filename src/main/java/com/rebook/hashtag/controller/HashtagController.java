package com.rebook.hashtag.controller;

import com.rebook.hashtag.controller.requeest.HashtagRequest;
import com.rebook.hashtag.controller.response.HashtagResponse;
import com.rebook.hashtag.service.HashtagService;
import com.rebook.hashtag.service.command.HashtagCommand;
import com.rebook.hashtag.service.dto.HashtagDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/hashtags")
@RestController
public class HashtagController {

    private final HashtagService hashtagService;

    @PostMapping
    public ResponseEntity<HashtagResponse> createHashtag(
            @RequestBody @Valid final HashtagRequest hashtagRequest
    ) {
        HashtagCommand hashtagCommand = HashtagCommand.of(hashtagRequest);
        HashtagDto hashtagDto = hashtagService.create(hashtagCommand);
        final HashtagResponse hashtag = HashtagResponse.fromDto(hashtagDto);

        return ResponseEntity.created(URI.create("/api/v1/hashtags/" + hashtag.getId())).body(hashtag);
    }

    @GetMapping
    public ResponseEntity<List<HashtagResponse>> getHashtags() {
        List<HashtagDto> hashtagDtos = hashtagService.getHashtags();

        List<HashtagResponse> hashtags = hashtagDtos.stream()
                .map(HashtagResponse::fromDto)
                .toList();

        return ResponseEntity.ok().body(hashtags);
    }

    @GetMapping("/{hashtagId}")
    public ResponseEntity<HashtagResponse> getHashtag(@PathVariable final Long hashtagId) {
        HashtagDto hashtagDto = hashtagService.getHashtag(hashtagId);
        HashtagResponse hashtag = HashtagResponse.fromDto(hashtagDto);

        return ResponseEntity.ok().body(hashtag);
    }

    @PutMapping({"/{hashtagId}"})
    public ResponseEntity<HashtagResponse> updateHashtag(
            @PathVariable final Long hashtagId,
            @RequestBody @Valid final HashtagRequest hashtagRequest
    ) {
        HashtagCommand hashtagCommand = HashtagCommand.of(hashtagRequest);
        hashtagService.update(hashtagId, hashtagCommand);
        HashtagResponse updatedHashtag = HashtagResponse.fromDto(hashtagService.getHashtag(hashtagId));

        return ResponseEntity.ok().body(updatedHashtag);
    }

    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<Void> deleteHashtag(@PathVariable final Long hashtagId) {
        hashtagService.softDelete(hashtagId);
        return ResponseEntity.noContent().build();
    }
}
