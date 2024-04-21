package com.rebook.review.domain;

import com.rebook.book.domain.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class Review {
    private BookEntity book;
    private Long id;
    private String content;
    private BigDecimal starRate;
    private ZonedDateTime createdAt;

    public Review() {
    }

    public static Review from(final ReviewEntity reviewEntity) {
        return new Review(
                reviewEntity.getBook(),
                reviewEntity.getId(),
                reviewEntity.getContent(),
                reviewEntity.getStarRate(),
                reviewEntity.getCreatedAt());}
    }
