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
    private String content;
    private BigDecimal starRate;
    private ZonedDateTime createdAt;

    public static ReviewResponse of(final Review review) {
        return new ReviewResponse(
                review.getContent(),
                review.getStarRate(),
                review.getCreatedAt());
    }

    @JsonProperty("starRate")
    public String getStarRate() {
        return starRate.toString();
    }
}
