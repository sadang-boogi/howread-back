package com.rebook.hashtag.controller;

import com.rebook.auth.annotation.LoginRequiredForController;
import com.rebook.hashtag.controller.requeest.HashtagRequest;
import com.rebook.hashtag.controller.response.HashtagResponse;
import com.rebook.hashtag.service.HashtagService;
import com.rebook.hashtag.service.command.HashtagCommand;
import com.rebook.hashtag.service.dto.HashtagDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Hashtag", description = "Hashtag API")
@RequiredArgsConstructor
@LoginRequiredForController
@RequestMapping("/api/v1/hashtags")
@RestController
public class HashtagController {

    private final HashtagService hashtagService;

    @Operation(summary = "Create Hashtag", description = "해시태그를 등록한다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping
    public ResponseEntity<HashtagResponse> createHashtag(
            @RequestBody @Valid final HashtagRequest hashtagRequest
    ) {
        HashtagCommand hashtagCommand = HashtagCommand.of(hashtagRequest);
        HashtagDto hashtagDto = hashtagService.create(hashtagCommand);
        final HashtagResponse hashtag = HashtagResponse.fromDto(hashtagDto);

        return ResponseEntity.created(URI.create("/api/v1/hashtags/" + hashtag.getId())).body(hashtag);
    }

    @Operation(summary = "Get All Hashtags", description = "등록된 모든 해시태그를 조회한다.")
    @GetMapping
    public ResponseEntity<List<HashtagResponse>> getHashtags() {
        List<HashtagDto> hashtagDtos = hashtagService.getHashtags();

        List<HashtagResponse> hashtags = hashtagDtos.stream()
                .map(HashtagResponse::fromDto)
                .toList();

        return ResponseEntity.ok().body(hashtags);
    }

    @Operation(summary = "Get Hashtag", description = "hashtagId와 일치하는 단일 해시태그를 조회한다.")
    @GetMapping("/{hashtagId}")
    public ResponseEntity<HashtagResponse> getHashtag(@PathVariable final Long hashtagId) {
        HashtagDto hashtagDto = hashtagService.getHashtag(hashtagId);
        HashtagResponse hashtag = HashtagResponse.fromDto(hashtagDto);

        return ResponseEntity.ok().body(hashtag);
    }

    @Operation(summary = "Update Hashtag", description = "hashtagId와 일치하는 해시태그의 정보를 수정한다.")
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

    @Operation(summary = "Delete Hashtag", description = "hashtagId와 일치하는 해시태그를 삭제한다.")
    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<Void> deleteHashtag(@PathVariable final Long hashtagId) {
        hashtagService.softDelete(hashtagId);
        return ResponseEntity.noContent().build();
    }
}
