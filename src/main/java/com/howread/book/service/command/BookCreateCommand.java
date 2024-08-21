package com.howread.book.service.command;

import com.howread.book.controller.request.BookCreateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class BookCreateCommand {

    private String title;
    private String author;
    private String thumbnailUrl;
    private String isbn;
    private List<Long> hashtagIds;

    @Builder
    public BookCreateCommand(String title, String author, String thumbnailUrl, String isbn ,List<Long> hashtagIds) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
        this.hashtagIds = hashtagIds;
    }

    public static BookCreateCommand from(BookCreateRequest request) {
        return BookCreateCommand.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .thumbnailUrl(request.getThumbnailUrl())
                .hashtagIds(request.getHashtagIds())
                .build();
    }
}
