package com.rebook.review.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebook.review.domain.Review;
import com.rebook.review.domain.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ReviewResponse {
    private String content;
    private BigDecimal starRate;

    public static ReviewResponse of(final Review review){
        return new ReviewResponse(
                review.getContent(),
                review.getStarRate()
        );
    }

    @JsonProperty("starRate")
    public String getStarRate() {
        return starRate.toString();
    }
}
