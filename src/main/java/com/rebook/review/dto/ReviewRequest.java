package com.rebook.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ReviewRequest {
    @NotBlank(message ="리뷰 내용을 입력해주세요.")
    private String content;
    @NotNull(message = "별점을 등록해주세요.")
    private BigDecimal starRate;

}
