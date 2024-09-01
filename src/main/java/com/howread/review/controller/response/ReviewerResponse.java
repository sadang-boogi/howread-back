package com.howread.review.controller.response;

import com.howread.review.service.dto.ReviewerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewerResponse {
    private Long id;
    private String name;
    private String avatarUrl;

    public static ReviewerResponse from(ReviewerDto reviewerDto) {
        return new ReviewerResponse(reviewerDto.getId(), reviewerDto.getName(), reviewerDto.getAvatarUrl());
    }
}
