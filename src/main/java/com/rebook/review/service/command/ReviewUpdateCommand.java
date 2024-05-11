package com.rebook.review.service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ReviewUpdateCommand {
    private Long bookId;
    private Long reviewId;
    private String content;
    private BigDecimal score;
}