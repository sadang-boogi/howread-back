package com.rebook.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_hashtag")
public class BookHashTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTagEntity hashTag;
}
