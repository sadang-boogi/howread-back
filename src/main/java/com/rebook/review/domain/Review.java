package com.rebook.review.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Review {
    private Long id;
    private String content;
    private BigDecimal starRate;
    private ZonedDateTime createdAt;

    public Review() {
    }

    public static Review of(final ReviewEntity reviewEntity) {
        return new Review(
                reviewEntity.getId(),
                reviewEntity.getContent(),
                reviewEntity.getStarRate(),
                reviewEntity.getCreatedAt());
    }

    public Review(Long id, String content, BigDecimal starRate, ZonedDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.starRate = starRate;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public BigDecimal getStarRate() {
        return starRate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
