package com.howread.review.service;

import com.howread.book.domain.BookEntity;
import com.howread.book.repository.BookRepository;
import com.howread.common.exception.NotFoundException;
import com.howread.review.domain.ReviewEntity;
import com.howread.review.repository.ReviewRepository;
import com.howread.review.service.command.ReviewSaveCommand;
import com.howread.review.service.command.ReviewUpdateCommand;
import com.howread.review.service.dto.ReviewDto;
import com.howread.user.domain.UserEntity;
import com.howread.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.howread.common.exception.ExceptionCode.NOT_FOUND_BOOK_ID;
import static com.howread.common.exception.ExceptionCode.NOT_FOUND_USER_ID;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public ReviewDto save(ReviewSaveCommand reviewCommand, Long userId) {
        BookEntity book = bookRepository.findById(reviewCommand.getBookId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_ID));

        ReviewEntity reviewEntity = ReviewEntity.of(book, user, reviewCommand.getContent(), reviewCommand.getScore());
        ReviewEntity savedReview = reviewRepository.save(reviewEntity);

        return ReviewDto.fromEntity(savedReview);
    }

    @Transactional(readOnly = true)
    public Slice<ReviewDto> getReviewsWithBookId(Long bookId, Pageable pageable) {
        Slice<ReviewEntity> reviewEntities = reviewRepository.findAllBy(bookId, pageable);

        List<ReviewDto> reviewDtos = reviewEntities.getContent()
                .stream()
                .map(ReviewDto::fromEntity)
                .toList();

        return new SliceImpl<>(reviewDtos, pageable, reviewEntities.hasNext());
    }

    @Transactional
    public ReviewDto update(ReviewUpdateCommand reviewCommand, Long userId) {

        // 사용자 ID와 리뷰 ID로 리뷰 조회
        ReviewEntity review = reviewRepository.findByIdAndUserId(reviewCommand.getReviewId(), userId)
                .orElseThrow(() -> new NotFoundException("리뷰 수정 실패", "리뷰를 찾을 수 없습니다."));

        // 기존 리뷰 엔티티 업데이트
        review.update(review.getBook(), reviewCommand.getContent(), reviewCommand.getScore());

        return ReviewDto.fromEntity(review);
    }

    @Transactional
    public void softDelete(Long reviewId, Long userId) {
        ReviewEntity review = reviewRepository.findByIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new NotFoundException("리뷰 삭제 실패", "리뷰를 찾을 수 없습니다."));

        review.softDelete();
    }

}
