package com.rebook.review.service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ReviewSaveCommand {
    private Long bookId;
    private String content;
    private BigDecimal score;
}