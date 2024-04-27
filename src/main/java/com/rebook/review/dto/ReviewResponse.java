package com.rebook.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebook.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ReviewResponse {
    private Long id;
    private String content;
    private BigDecimal starRate;
    private ZonedDateTime createdAt;

    public static ReviewResponse from(final Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getStarRate(),
                review.getCreatedAt());
    }

    @JsonProperty("starRate")
    public String getStarRate() {
        return starRate.toString();
    }
}
