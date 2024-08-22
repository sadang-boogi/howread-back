package com.howread.review.service.dto;

import com.howread.book.domain.BookEntity;
import com.howread.review.domain.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewDto {
    private BookEntity book;
    private Long id; // 리뷰의 아이디
    private String content;
    private ReviewerDto reviewer;
    private BigDecimal score;
    private ZonedDateTime createdAt;

    public static ReviewDto fromEntity(ReviewEntity reviewEntity) {
        return new ReviewDto(reviewEntity.getBook(),
                reviewEntity.getId(),
                reviewEntity.getContent(),
                ReviewerDto.fromUserEntity(reviewEntity.getUser()),
                reviewEntity.getScore(),
                reviewEntity.getCreatedAt());
    }
}
