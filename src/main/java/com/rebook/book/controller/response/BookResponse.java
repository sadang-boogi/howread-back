package com.rebook.book.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rebook.book.domain.BookEntity;
import com.rebook.book.domain.BookHashtagEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String thumbnailUrl;
    private List<String> hashtags;
    private BigDecimal rating;

    public static BookResponse from(final BookEntity book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getThumbnailUrl(),
                book.getBookHashTags().stream()
                        .map(BookHashtagEntity::getHashTag)
                        .map(HashtagEntity::getName)
                        .toList(),
                book.getRating()
        );
    }

    @JsonProperty("averageStarRate")
    public String getRating() {
        return rating.toString();
    }
}
