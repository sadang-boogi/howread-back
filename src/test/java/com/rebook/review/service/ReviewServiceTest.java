package com.rebook.review.service;

import com.rebook.book.domain.BookEntity;
import com.rebook.book.repository.BookRepository;
import com.rebook.review.domain.Review;
import com.rebook.review.dto.ReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("책 id로 review 저장")
    @Test
    void saveReview(){

        //given
        //책 저장
        BookEntity savedBook = bookRepository.save(new BookEntity(null, "객체 지향", "조영호", "defalut-image.jpa"));
        //bookId로 reviewRequest 생성
        ReviewRequest reviewRequest = new ReviewRequest( "review for 객체지향",new BigDecimal("4.5"));

        //when 리뷰 저장
        Review savedReview = reviewService.save(savedBook.getId(),reviewRequest);

        //then 저장한 책의 book Id와 저장된 리뷰의 bookId 비교
        assertEquals(savedBook.getId(),savedReview.getBook().getId());
        assertEquals(savedReview.getContent(),reviewRequest.getContent());
        assertEquals(savedReview.getStarRate(),reviewRequest.getStarRate());

    }


}