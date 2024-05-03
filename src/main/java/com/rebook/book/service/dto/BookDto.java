package com.rebook.book.service.dto;

import com.rebook.book.domain.BookEntity;
import com.rebook.book.domain.BookHashtagEntity;
import com.rebook.hashtag.service.dto.HashtagDto;
import com.rebook.review.service.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class BookDto {

    private Long id;

    private String title;

    private String author;

    private String thumbnailUrl;

    private List<HashtagDto> hashtags;

    private List<ReviewDto> reviews;

    private BigDecimal rating;

    @Builder
    private BookDto(Long id, String title, String author, String thumbnailUrl, List<HashtagDto> hashtags, List<ReviewDto> reviews, BigDecimal rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.hashtags = hashtags;
        this.reviews = reviews;
        this.rating = rating;
    }

    public static BookDto fromEntity(BookEntity bookEntity) {
        return BookDto.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .thumbnailUrl(bookEntity.getThumbnailUrl())
                .hashtags(bookEntity.getBookHashtags().stream()
                        .map(BookHashtagEntity::getHashTag)
                        .map(HashtagDto::fromEntity)
                        .toList())
                .reviews(bookEntity.getReviews().stream()
                        .map(ReviewDto::fromEntity)
                        .toList())
                .rating(bookEntity.getRating())
                .build();
    }
}
