package com.rebook.book.controller.request;

import com.rebook.book.service.command.BookCreateCommand;
import jakarta.validation.constraints.NotBlank;
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

    private List<Long> hashtagIds = new ArrayList<>();

    @Builder
    private BookCreateRequest(String title, String author, String thumbnailUrl, List<Long> hashtagIds) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.hashtagIds = hashtagIds;
    }

    public BookCreateCommand toCommand() {
        return BookCreateCommand.builder()
                .title(title)
                .author(author)
                .thumbnailUrl(thumbnailUrl)
                .hashtagIds(hashtagIds)
                .build();
    }
}
