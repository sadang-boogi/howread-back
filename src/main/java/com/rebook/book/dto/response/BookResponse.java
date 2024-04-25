package com.rebook.book.dto.response;

import com.rebook.book.domain.entity.BookEntity;
import com.rebook.book.domain.entity.BookHashtagEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String thumbnailUrl;
    private List<String> hashtags;

    public static BookResponse from(final BookEntity book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getThumbnailUrl(),
                book.getBookHashTags().stream()
                        .map(BookHashtagEntity::getHashTag)
                        .map(HashtagEntity::getName)
                        .toList()
        );
    }
}
