package com.rebook.review.service;

import com.rebook.review.domain.Review;
import com.rebook.review.domain.ReviewEntity;
import com.rebook.review.dto.ReviewRequest;
import com.rebook.review.dto.ReviewResponse;
import com.rebook.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @Transactional
    public Review save(ReviewRequest reviewRequest){
        ReviewEntity reviewEntity = ReviewEntity.of(reviewRequest);
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        return Review.of(savedReview);
    }

    public List<Review> getReviews(){
        List<ReviewEntity> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(Review::of)
                .toList();
    }
}
