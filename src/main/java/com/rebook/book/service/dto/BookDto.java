package com.rebook.book.service.dto;

import com.rebook.book.domain.BookEntity;
import com.rebook.book.domain.BookHashtagEntity;
import com.rebook.hashtag.service.dto.HashtagDto;
import com.rebook.review.service.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
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

    private List<BookReactionDto> reactions = new ArrayList<>();

    @Builder
    private BookDto(Long id, String title, String author, String thumbnailUrl, String isbn, List<HashtagDto> hashtags, List<ReviewDto> reviews, BigDecimal rating, List<BookReactionDto> reactions) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
        this.hashtags = hashtags;
        this.reviews = reviews;
        this.rating = rating;
        if (reactions != null) {
            this.reactions = reactions;
        }
    }

    public void addReaction(BookReactionDto reaction) {
        if (this.reactions == null) {
            this.reactions = new ArrayList<>();
        }
        this.reactions.add(reaction);
    }

    public static BookDto from(BookEntity bookEntity, List<BookReactionDto> reactions) {
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
                .reactions(reactions != null ? reactions : new ArrayList<>())
                .build();
    }

    public static BookDto from(BookEntity bookEntity) {
        return from(bookEntity, null);  // 리액션이 없는 경우
    }

}
