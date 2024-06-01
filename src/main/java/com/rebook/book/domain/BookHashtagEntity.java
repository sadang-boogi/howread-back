package com.rebook.book.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@SQLRestriction(value = "is_deleted = false")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book_hashtag")
@Entity
public class BookHashtagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private HashtagEntity hashtag;

    private BookHashtagEntity(BookEntity book, HashtagEntity hashtag) {
        this.book = book;
        this.hashtag = hashtag;
    }

    public static BookHashtagEntity of(BookEntity book, HashtagEntity hashtag) {
        return new BookHashtagEntity(
                book,
                hashtag
        );
    }

    public void addBook(BookEntity book) {
        this.book = book;
    }
}
