package com.rebook.review.controller;

import com.rebook.auth.annotation.CurrentUser;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.schema.ListResponse;
import com.rebook.jwt.service.JwtService;
import com.rebook.review.controller.request.ReviewSaveRequest;
import com.rebook.review.controller.request.ReviewUpdateRequest;
import com.rebook.review.controller.response.ReviewResponse;
import com.rebook.review.service.command.ReviewSaveCommand;
import com.rebook.review.service.command.ReviewUpdateCommand;
import com.rebook.review.service.dto.ReviewDto;
import com.rebook.review.service.ReviewService;
import com.rebook.user.service.dto.LoggedInUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final JwtService jwtService;

    @PostMapping
    @LoginRequired
    @Operation(summary = "Create Review for a Book", description = "userId로 해당 책에 리뷰를 작성한다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    public ResponseEntity<ReviewResponse> saveReview(
            @PathVariable("bookId") Long bookId,
            @Valid @RequestBody final ReviewSaveRequest reviewRequest,
            @CurrentUser LoggedInUser user

    ) {
        ReviewSaveCommand reviewCommand = reviewRequest.toCommand(bookId, reviewRequest);
        ReviewDto savedReview = reviewService.save(reviewCommand, user.getUserId());
        ReviewResponse reviewResponse = ReviewResponse.fromDto(savedReview);
        URI location = URI.create(String.format("/api/v1/books/%d/reviews/%d", bookId, savedReview.getId()));
        return ResponseEntity.created(location).body(reviewResponse);
    }

    @GetMapping
    @Operation(summary = "Get All Reviews for a Book", description = "해당 책의 작성리뷰를 조회한다.")
    public ResponseEntity<ListResponse<ReviewResponse>> getReviews(@PathVariable("bookId") Long bookId) {
        final List<ReviewDto> reviewDtos = reviewService.getReviewsWithBookId(bookId);
        List<ReviewResponse> reviewResponses = reviewDtos.stream()
                .map(ReviewResponse::fromDto)
                .toList();
        ListResponse<ReviewResponse> responses = new ListResponse<>(reviewResponses);
        return ResponseEntity.ok().body(responses);
    }

    @LoginRequired
    @PutMapping("/{reviewId}")
    @Operation(summary = "Update a Review for a Book", description = "해당 책의 특정 리뷰를 수정한다.")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable("bookId") Long bookId,
            @PathVariable("reviewId") Long reviewId,
            @Valid @RequestBody final ReviewUpdateRequest reviewRequest,
            @CurrentUser LoggedInUser user
    ) {
        ReviewUpdateCommand reviewCommand = reviewRequest.toCommand(bookId, reviewId, reviewRequest);
        ReviewDto updatedReview = reviewService.update(reviewCommand, user.getUserId());
        ReviewResponse reviewResponse = ReviewResponse.fromDto(updatedReview);
        return ResponseEntity.ok(reviewResponse);
    }

    @LoginRequired
    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a Review for a Book", description = "해당 책의 특정 리뷰를 삭제한다.")
    public ResponseEntity<Void> softDeleteReview(
            @PathVariable("reviewId") Long reviewId,
            @CurrentUser LoggedInUser user
    ) {
        reviewService.softDelete(reviewId, user.getUserId());
        return ResponseEntity.noContent().build();
    }
}
