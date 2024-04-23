package com.rebook.book.domain.entity;

import com.rebook.hashtag.domain.HashtagEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_hashtag")
@Entity
public class BookHashtagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private HashtagEntity hashTag;

    private BookHashtagEntity(BookEntity book, HashtagEntity hashTag) {
        this.book = book;
        this.hashTag = hashTag;
    }

    public static BookHashtagEntity of(BookEntity book, HashtagEntity hashtag) {
        return new BookHashtagEntity(
                book,
                hashtag
        );
    }
}
