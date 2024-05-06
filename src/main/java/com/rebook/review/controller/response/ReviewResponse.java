package com.rebook.review.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebook.review.service.dto.ReviewDto;
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

    public static ReviewResponse fromDto(ReviewDto review) {
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getScore(),
                review.getCreatedAt());
    }

    @JsonProperty("starRate")
    public String getStarRate() {
        return starRate.toString();
    }
}
