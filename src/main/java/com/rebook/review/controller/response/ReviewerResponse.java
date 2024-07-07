package com.rebook.review.controller.response;

import com.rebook.review.service.dto.ReviewerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewerResponse {
    private Long id;
    private String name;

    public static ReviewerResponse from(ReviewerDto reviewerDto) {
        return new ReviewerResponse(reviewerDto.getId(), reviewerDto.getName());
    }
}