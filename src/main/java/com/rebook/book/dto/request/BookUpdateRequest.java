package com.rebook.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookUpdateRequest {

    @NotBlank(message = "책 제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "저자를 입력해주세요.")
    private String author;

    private String thumbnailUrl;

    private List<Long> hashtagIds;
}
