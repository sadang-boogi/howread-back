package com.rebook.book.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @NotBlank(message = "ISBN 값을 입력해주세요.")
    @Pattern(
            regexp = "^(978|979)\\d{10}$",
            message = "유효한 13자리 ISBN 형식을 입력해주세요."
    )
    private String isbn;

    private List<Long> hashtagIds = new ArrayList<>();
}
