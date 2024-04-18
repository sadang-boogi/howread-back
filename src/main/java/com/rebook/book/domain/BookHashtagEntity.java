package com.rebook.book.domain;

import com.rebook.hashtag.domain.HashtagEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "book_hashtag")
public class BookHashtagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashtagEntity hashTag;
}
