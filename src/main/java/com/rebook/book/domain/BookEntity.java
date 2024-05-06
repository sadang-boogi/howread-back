package com.rebook.book.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.review.domain.ReviewEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SQLRestriction(value = "is_deleted = false")
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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookHashtagEntity> bookHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();

    private BookEntity(
            final Long id,
            final String title,
            final String author,
            final String thumbnailUrl
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static BookEntity of(
            final String title,
            final String author,
            final String thumbnailUrl
    ) {
        return new BookEntity(
                null,
                title,
                author,
                thumbnailUrl
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

    public void addHashtag(HashtagEntity hashtag) {
        BookHashtagEntity bookHashtag = BookHashtagEntity.of(this, hashtag);
        bookHashtags.add(bookHashtag);
    }

    public void update(BookEntity updateBook) {
        this.title = updateBook.getTitle(); //todo: 확인 필요: 엔티티에서 Command 객체 사용해도 되는가?
        this.author = updateBook.getAuthor();
        this.thumbnailUrl = updateBook.getThumbnailUrl();
    }

    public void clearHashtags() {
        this.bookHashtags.clear();
    }

    public String getThumbnailUrl() {
        return Objects.requireNonNullElse(thumbnailUrl, DEFAULT_IMAGE_NAME);
    }
}
