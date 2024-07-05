package com.rebook.book.service.command;

import com.rebook.book.controller.request.BookUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BookUpdateCommand {

    private String title;

    private String author;

    private String thumbnailUrl;

    private String isbn;

    private List<Long> hashtagIds;

    @Builder
    private BookUpdateCommand(String title, String author, String thumbnailUrl, String isbn, List<Long> hashtagIds) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
        this.hashtagIds = hashtagIds;
    }

    public static BookUpdateCommand from(BookUpdateRequest request) {
        return BookUpdateCommand.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .thumbnailUrl(request.getThumbnailUrl())
                .isbn(request.getIsbn())
                .hashtagIds(request.getHashtagIds())
                .build();
    }
}
