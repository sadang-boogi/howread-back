package com.rebook.review.dto;

import com.rebook.book.domain.entity.BookEntity;
import com.rebook.review.domain.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReviewDTO {
    private BookEntity book;
    private Long id;
    private String content;
    private BigDecimal starRate;
    private ZonedDateTime createdAt;

    public static ReviewDTO fromEntity(ReviewEntity reviewEntity) {
        return new ReviewDTO(reviewEntity.getBook(),
                reviewEntity.getId(),
                reviewEntity.getContent(),
                reviewEntity.getStarRate(),
                reviewEntity.getCreatedAt());
    }
}
