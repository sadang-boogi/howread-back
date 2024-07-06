package com.rebook.book.controller.request;

import com.rebook.book.service.command.BookCreateCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class BookCreateRequest {

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

    @Builder
    private BookCreateRequest(String title, String author, String thumbnailUrl, String isbn, List<Long> hashtagIds) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
        this.hashtagIds = hashtagIds;
    }

    public BookCreateCommand toCommand() {
        return BookCreateCommand.builder()
                .title(title)
                .author(author)
                .thumbnailUrl(thumbnailUrl)
                .isbn(isbn)
                .hashtagIds(hashtagIds)
                .build();
    }
}
