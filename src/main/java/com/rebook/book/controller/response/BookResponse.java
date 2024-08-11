package com.rebook.book.controller.response;

import com.rebook.book.service.dto.BookDto;
import com.rebook.hashtag.service.dto.HashtagDto;
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
    private BookReactionResponse reaction;

    public static BookResponse from(final BookDto book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getThumbnailUrl(),
                book.getHashtags().stream()
                        .map(HashtagDto::getName)
                        .toList(),
                book.getRating(),
                BookReactionResponse.from(book.getReaction())
        );
    }

}
