package com.rebook.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookCreateRequest {

    @NotBlank(message = "책 제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "저자를 입력해주세요.")
    private final String author;

    private final String thumbnailUrl;
}
