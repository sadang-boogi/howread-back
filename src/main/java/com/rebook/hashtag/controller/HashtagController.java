package com.rebook.hashtag.controller;

import com.rebook.hashtag.controller.requeest.HashtagRequest;
import com.rebook.hashtag.controller.response.HashtagResponse;
import com.rebook.hashtag.service.HashtagService;
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
        HashtagResponse hashtag = hashtagService.create(hashtagRequest);

        return ResponseEntity.created(URI.create("/api/v1/hashtags/" + hashtag.getId())).body(hashtag);
    }

    @GetMapping
    public ResponseEntity<List<HashtagResponse>> getHashtags() {
        final List<HashtagResponse> hashtags = hashtagService.getHashtags();

        return ResponseEntity.ok().body(hashtags);
    }

    @GetMapping("/{hashtagId}")
    public ResponseEntity<HashtagResponse> getHashtag(@PathVariable final Long hashtagId) {
        HashtagResponse hashtag = hashtagService.getHashtag(hashtagId);

        return ResponseEntity.ok().body(hashtag);
    }

    @PutMapping({"/{hashtagId}"})
    public ResponseEntity<HashtagResponse> updateHashtag(
            @PathVariable final Long hashtagId,
            @RequestBody @Valid final HashtagRequest hashtagRequest
    ) {
        hashtagService.update(hashtagId, hashtagRequest);
        HashtagResponse updatedHashtag = hashtagService.getHashtag(hashtagId);

        return ResponseEntity.ok().body(updatedHashtag);
    }

    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<Void> deleteHashtag(@PathVariable final Long hashtagId) {
        hashtagService.delete(hashtagId);

        return ResponseEntity.noContent().build();
    }
}
