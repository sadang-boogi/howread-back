package com.rebook.book.dto;

import com.rebook.book.domain.BookEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookResponse {

    private final String title;
    private final String author;
    private final String thumbnailUrl;

    public static BookResponse of(final BookEntity book) {
        return new BookResponse(
                book.getTitle(),
                book.getAuthor(),
                book.getThumbnailUrl()
        );
    }
}
