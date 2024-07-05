package com.rebook.book.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.review.domain.ReviewEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SQLRestriction(value = "is_deleted = false")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "book")
@Entity
public class BookEntity extends BaseEntity {

    private static final String DEFAULT_IMAGE_NAME = "default_image.jpeg";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Comment("책 대표 표지 이미지")
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookHashtagEntity> bookHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();

    private BookEntity(
            final Long id,
            final String title,
            final String author,
            final String thumbnailUrl,
            final String isbn
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
    }

    public static BookEntity of(
            final String title,
            final String author,
            final String thumbnailUrl,
            final String isbn
    ) {
        return new BookEntity(
                null,
                title,
                author,
                thumbnailUrl,
                isbn
        );
    }

    public BigDecimal getRating() {
        if (reviews.isEmpty()) {
            return BigDecimal.ZERO; // 리뷰가 없으면 0 반환
        }
        BigDecimal sum = reviews.stream()
                .map(ReviewEntity::getScore)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(reviews.size()), 3, RoundingMode.HALF_UP);
    }

    public void addHashtag(BookHashtagEntity... hashtags) {
        for (BookHashtagEntity hashtag : hashtags) {
            this.bookHashtags.add(hashtag);
            hashtag.addBook(this);
        }
    }

    public void clearHashtag() {
        this.bookHashtags.clear();
    }

    public void update(BookEntity updateBook) {
        this.title = updateBook.getTitle();
        this.author = updateBook.getAuthor();
        this.thumbnailUrl = updateBook.getThumbnailUrl();
        this.isbn = updateBook.getIsbn();
    }

    public String getThumbnailUrl() {
        return Objects.requireNonNullElse(thumbnailUrl, DEFAULT_IMAGE_NAME);
    }
}
