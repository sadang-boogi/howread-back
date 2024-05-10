package com.rebook.review.service.command;

import com.rebook.review.controller.request.ReviewRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SaveReviewCommand {
    private Long bookId;
    private String content;
    private BigDecimal score;

    public static SaveReviewCommand from(Long bookId, String content, BigDecimal score) {
        return new SaveReviewCommand(
                bookId,
                content,
                score);
    }
}
