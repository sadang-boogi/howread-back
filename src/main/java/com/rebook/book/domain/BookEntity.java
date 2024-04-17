package com.rebook.book.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.review.domain.ReviewEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @OneToMany(mappedBy = "book")
    private List<BookHashtagEntity> bookHashTags = new ArrayList<>();

    @OneToMany
    private List<ReviewEntity> reviews = new ArrayList<>();

    public BookEntity(
            final Long id,
            final String title,
            final String author,
            final String thumbnailUrl
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.thumbnailUrl = getThumbnailImageName(thumbnailUrl);
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

    private String getThumbnailImageName(final String thumbnailUrl) {
        return Objects.requireNonNullElse(thumbnailUrl, DEFAULT_IMAGE_NAME);
    }
}
