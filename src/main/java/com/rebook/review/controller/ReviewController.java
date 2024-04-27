package com.rebook.review.controller;

import com.rebook.review.domain.Review;
import com.rebook.review.dto.ReviewRequest;
import com.rebook.review.dto.ReviewResponse;
import com.rebook.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping
    @Operation(summary = "Create Review for a Book", description = "해당 책에 리뷰를 작성한다.")
    public ResponseEntity<ReviewResponse> saveReview(
            @PathVariable("bookId") Long bookId,
            @Valid @RequestBody final ReviewRequest reviewRequest){
        Review savedReview = reviewService.save(bookId,reviewRequest);
        ReviewResponse reviewResponse = ReviewResponse.from(savedReview);
        URI location = URI.create(String.format("/api/v1/books/%d/reviews/%d", bookId, savedReview.getId()));
        return ResponseEntity.created(location).body(reviewResponse);
    }

    @GetMapping
    @Operation(summary = "Get All Reviews for a Book", description = "해당 책의 작성리뷰를 조회한다.")
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable("bookId") Long bookId){
        final List<Review> reviews = reviewService.getReviewsWithBookId(bookId);
        List<ReviewResponse> responses = reviews.stream()
                .map(ReviewResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update a Review for a Book", description = "해당 책의 특정 리뷰를 수정한다.")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable("bookId") Long bookId,
            @PathVariable("reviewId") Long reviewId,
            @Valid @RequestBody final ReviewRequest reviewRequest) {
        Review updatedReview = reviewService.update(bookId, reviewId, reviewRequest);
        ReviewResponse reviewResponse = ReviewResponse.from(updatedReview);
        return ResponseEntity.ok(reviewResponse);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a Review for a Book", description = "해당 책의 특정 리뷰를 삭제한다.")
    public ResponseEntity<Void> softDeleteReview(
            @PathVariable("reviewId") Long reviewId) {
        reviewService.softDelete(reviewId);
        return ResponseEntity.noContent().build();
    }






}
