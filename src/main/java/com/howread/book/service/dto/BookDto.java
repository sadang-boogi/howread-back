package com.howread.book.service.dto;

import com.howread.book.domain.BookEntity;
import com.howread.book.domain.BookHashtagEntity;
import com.howread.hashtag.service.dto.HashtagDto;
import com.howread.review.service.dto.ReviewDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private String author;

    private String thumbnailUrl;

    private String isbn;

    private List<HashtagDto> hashtags;

    private List<ReviewDto> reviews;

    private BigDecimal rating;

    private BookReactionDto reaction;

    @Builder
    private BookDto(Long id, String title, String author, String thumbnailUrl, String isbn, List<HashtagDto> hashtags, List<ReviewDto> reviews, BigDecimal rating, BookReactionDto reaction) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
        this.hashtags = hashtags;
        this.reviews = reviews;
        this.rating = rating;
        this.reaction = reaction;
    }

    public static BookDto from(BookEntity bookEntity, BookReactionDto reaction) {
        return BookDto.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .thumbnailUrl(bookEntity.getThumbnailUrl())
                .isbn(bookEntity.getIsbn())
                .hashtags(bookEntity.getBookHashtags().stream()
                        .map(BookHashtagEntity::getHashtag)
                        .map(HashtagDto::fromEntity)
                        .toList())
                .reviews(bookEntity.getReviews().stream()
                        .map(ReviewDto::fromEntity)
                        .toList())
                .rating(bookEntity.getRating())
                .reaction(reaction)
                .build();
    }

    public static BookDto from(BookEntity bookEntity) {
        return from(bookEntity, new BookReactionDto(false, false));  // 리액션이 없는 경우
    }

}
