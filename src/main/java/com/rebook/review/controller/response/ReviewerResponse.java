package com.rebook.review.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewerResponse {
    private Long id;
    private String name;

    public static ReviewerResponse from(Long userId, String userName) {
        return new ReviewerResponse(userId, userName);
    }
}
