package com.rebook.review.service;

import com.rebook.book.domain.BookEntity;
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
}
