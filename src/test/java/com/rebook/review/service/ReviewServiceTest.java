package com.rebook.review.service;

import com.rebook.book.domain.BookEntity;
import com.rebook.book.repository.BookRepository;
import com.rebook.review.domain.ReviewEntity;
import com.rebook.review.repository.ReviewRepository;
import com.rebook.review.service.dto.ReviewDto;
import com.rebook.user.domain.Role;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.util.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @DisplayName("bookId로 리뷰 목록 조회")
    @Test
    void getReviewsWithBookId() {
        // given
        // 책 객체
        BookEntity bookEntity = new BookEntity(1L, "오브젝트", "조영호", "test-image", "978-89-123456-0-0", Collections.emptyList(), Collections.emptyList());

        // 사용자
        UserEntity user = new UserEntity(1L, "test", "test", Role.USER, SocialType.GOOGLE, "1212");

        // 리뷰 객체
        ReviewEntity reviewEntity1 = ReviewEntity.of(bookEntity, user, "first content", BigDecimal.valueOf(4.5));
        ReviewEntity reviewEntity2 = ReviewEntity.of(bookEntity, user, "second content", BigDecimal.valueOf(3.5));
        List<ReviewEntity> reviews = List.of(reviewEntity1, reviewEntity2);

        Pageable pageable = PageRequest.of(0, 10);
        SliceImpl<ReviewEntity> reviewSlice = new SliceImpl<>(reviews, pageable, false);

        //bookId로 책 조회시 bookEntity 반환
        when(bookRepository.findById(bookEntity.getId())).thenReturn(Optional.of(bookEntity));
        //bookId로 리뷰 목록 조회시 reviews 반환
        when(reviewRepository.findAllBy(bookEntity.getId(), pageable)).thenReturn(reviewSlice);

        // When
        Slice<ReviewDto> result = reviewService.getReviewsWithBookId(bookEntity.getId(), pageable);

        // Then
        assertEquals(2, result.getContent().size());
        assertEquals("first content", result.getContent().get(0).getContent());
        assertEquals(BigDecimal.valueOf(4.5), result.getContent().get(0).getScore());
        assertEquals("second content", result.getContent().get(1).getContent());
        assertEquals(BigDecimal.valueOf(3.5), result.getContent().get(1).getScore());
    }
}
