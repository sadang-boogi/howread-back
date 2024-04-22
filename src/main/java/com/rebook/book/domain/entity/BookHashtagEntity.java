package com.rebook.book.domain.entity;

import com.rebook.hashtag.domain.HashtagEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "book_hashtag")
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
}
