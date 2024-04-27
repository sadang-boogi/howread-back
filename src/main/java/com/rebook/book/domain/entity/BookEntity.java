package com.rebook.book.domain.entity;

import com.rebook.book.dto.request.BookUpdateRequest;
import com.rebook.common.domain.BaseEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.review.domain.ReviewEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SQLRestriction(value = "is_deleted = false")
@SQLDelete(sql = "UPDATE book SET is_deleted = true WHERE id = ?")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<BookHashtagEntity> bookHashTags = new ArrayList<>();

    @OneToMany
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

    public void addHashtag(HashtagEntity hashtag) {
        BookHashtagEntity bookHashtag = BookHashtagEntity.of(this, hashtag);
        bookHashTags.add(bookHashtag);
    }

    public void update(BookUpdateRequest bookUpdateRequest) {
        this.title = bookUpdateRequest.getTitle();
        this.author = bookUpdateRequest.getAuthor();
        this.thumbnailUrl = bookUpdateRequest.getThumbnailUrl();
    }

    public void clearHashTags() {
        this.bookHashTags.clear();
    }

    public String getThumbnailUrl() {
        return Objects.requireNonNullElse(thumbnailUrl, DEFAULT_IMAGE_NAME);
    }
}