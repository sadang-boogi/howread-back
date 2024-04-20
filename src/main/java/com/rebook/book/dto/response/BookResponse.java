package com.rebook.book.dto.response;

import com.rebook.book.domain.BookEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BookResponse {

    private final Long id;
    private final String title;
    private final String author;
    private final String thumbnailUrl;

    public static BookResponse of(final BookEntity book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getThumbnailUrl()
        );
    }
}
