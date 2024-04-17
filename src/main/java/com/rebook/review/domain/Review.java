package com.rebook.review.domain;

import java.math.BigDecimal;

public class Review {
    private Long id;
    private String content;
    private BigDecimal starRate;

    public Review() {
    }
    public static Review of(final ReviewEntity reviewEntity){
        return new Review(reviewEntity.getId(), reviewEntity.getContent(),reviewEntity.getStarRate());
    }

    public Review(Long id, String content, BigDecimal starRate) {
        this.id = id;
        this.content = content;
        this.starRate = starRate;
    }
    public static Review of(final String content, final BigDecimal starRate){
        return new Review(null, content, starRate);
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
}
