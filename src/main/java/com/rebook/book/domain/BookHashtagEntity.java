package com.rebook.book.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE book_hashtag SET is_deleted = true WHERE id = ?")
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
