package com.rebook.review.service;

import com.rebook.book.domain.entity.BookEntity;
import com.rebook.book.repository.BookRepository;
import com.rebook.review.domain.Review;
import com.rebook.review.domain.ReviewEntity;
import com.rebook.review.dto.ReviewRequest;
import com.rebook.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }
    @Transactional
    public Review save(Long bookId,ReviewRequest reviewRequest){
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("bookId not found: " + bookId));
        ReviewEntity reviewEntity = ReviewEntity.of(book,reviewRequest);
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        return Review.from(savedReview);
    }

    public List<Review> getReviewsWithBookId(Long bookId){
        List<ReviewEntity> reviews = reviewRepository.findByBook_IdOrderByCreatedAtAsc(bookId);
        return reviews.stream()
                .map(Review::from)
                .toList();
    }

    @Transactional
    public Review update(Long bookId, Long reviewId, ReviewRequest reviewRequest) {
        // 존재하는 책인지 확인
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + bookId));

        // 리뷰 id로 리뷰 조회
        ReviewEntity review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with ID: " + reviewId));

        // 새로운 값으로 리뷰 업데이트
        review.setContent(reviewRequest.getContent());
        review.setStarRate(reviewRequest.getStarRate());

        return Review.from(review);
    }

    @Transactional
    public void delete(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
