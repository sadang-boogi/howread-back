package com.rebook.review.service;

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

    @DisplayName("리뷰 저장 후 id 반환")
    @Test
    void saveReview() {

        //given 리뷰 작성
        ReviewRequest reviewRequest = new ReviewRequest("리뷰 서비스 저장 요청", new BigDecimal("4.5"));

        //when 리뷰 저장
        Review savedReview = reviewService.save(reviewRequest);

        //then 저장된 리뷰 아이디 확인
        assertEquals(savedReview.getContent(), reviewRequest.getContent());
        assertEquals(savedReview.getStarRate(), reviewRequest.getStarRate());
    }
}