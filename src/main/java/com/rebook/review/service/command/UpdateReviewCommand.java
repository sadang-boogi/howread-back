package com.rebook.review.service.command;

import com.rebook.review.controller.request.ReviewRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UpdateReviewCommand {
    private Long bookId;
    private Long reviewId;
    private String content;
    private BigDecimal score;

    public static UpdateReviewCommand from(Long bookId, Long reviewId, String content, BigDecimal score) {
        return new UpdateReviewCommand(
                bookId,
                reviewId,
                content,
                score);
    }

}