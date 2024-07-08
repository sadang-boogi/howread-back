package com.rebook.review.controller;

import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.domain.PageInfo;
import com.rebook.common.schema.PageResponse;
import com.rebook.review.controller.request.ReviewSaveRequest;
import com.rebook.review.controller.request.ReviewUpdateRequest;
import com.rebook.review.controller.response.ReviewResponse;
import com.rebook.review.service.ReviewService;
import com.rebook.review.service.dto.ReviewDto;
import com.rebook.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/v1/books/{bookId}/reviews")
@Tag(name = "Review", description = "Review API")
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @LoginRequired
    @Operation(summary = "Create Review for a Book", description = "userId로 해당 책에 리뷰를 작성한다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<ReviewResponse> saveReview(
            @PathVariable("bookId") Long bookId,
            @Valid @RequestBody final ReviewSaveRequest reviewRequest,
            @Parameter(hidden = true) @Authenticated AuthClaims claims

    ) {
        ReviewDto savedReview = reviewService.save(
                reviewRequest.toCommand(bookId, claims.getUserId())
        );
        ReviewResponse reviewResponse = ReviewResponse.fromDto(savedReview);
        URI location = URI.create(String.format("/api/v1/books/%d/reviews/%d", bookId, savedReview.getId()));
        return ResponseEntity.created(location).body(reviewResponse);
    }

    @GetMapping
    @Operation(summary = "Get All Reviews for a Book", description = "해당 책의 작성리뷰를 조회한다.")
    public ResponseEntity<PageResponse<ReviewResponse>> getReviews(
            @PathVariable("bookId") Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Slice<ReviewDto> reviews = reviewService.getReviewsWithBookId(bookId, pageable);

        List<ReviewResponse> items = reviews.stream()
                .map(ReviewResponse::fromDto)
                .toList();

        PageInfo pageInfo = new PageInfo(reviews.getNumber(), reviews.getSize(), reviews.hasNext());

        PageResponse<ReviewResponse> responses = new PageResponse<>(items, pageInfo);

        return ResponseEntity.ok().body(responses);
    }


    @LoginRequired
    @PutMapping("/{reviewId}")
    @Operation(summary = "Update a Review for a Book", description = "해당 책의 특정 리뷰를 수정한다.")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable("reviewId") Long reviewId,
            @Valid @RequestBody final ReviewUpdateRequest reviewRequest,
            @Parameter(hidden = true) @Authenticated AuthClaims claims
    ) {
        ReviewDto updatedReview = reviewService.update(
                reviewRequest.toCommand(reviewId),
                claims.getUserId()
        );
        ReviewResponse reviewResponse = ReviewResponse.fromDto(updatedReview);
        return ResponseEntity.ok(reviewResponse);
    }

    @LoginRequired
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a Review for a Book", description = "해당 책의 특정 리뷰를 삭제한다.")
    public ResponseEntity<Void> softDeleteReview(
            @PathVariable("reviewId") Long reviewId,
            @Parameter(hidden = true) @Authenticated AuthClaims claims
    ) {
        reviewService.softDelete(reviewId, claims.getUserId());
        return ResponseEntity.noContent().build();
    }
}
