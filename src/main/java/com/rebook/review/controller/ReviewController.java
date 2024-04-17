package com.rebook.review.controller;

import com.rebook.review.domain.Review;
import com.rebook.review.dto.ReviewRequest;
import com.rebook.review.dto.ReviewResponse;
import com.rebook.review.repository.ReviewRepository;
import com.rebook.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/reviews")
@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<ReviewResponse> saveBook(@Valid final ReviewRequest reviewRequest){
        Review savedReview = reviewService.save(reviewRequest);
        ReviewResponse reviewResponse = ReviewResponse.of(savedReview);
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(){
        final List<Review> reviews = reviewService.getReviews();
        List<ReviewResponse> responses = reviews.stream()
                .map(ReviewResponse::of)
                .toList();
        return ResponseEntity.ok(responses);
    }


}
