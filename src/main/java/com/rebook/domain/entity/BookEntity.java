package com.rebook.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
public class BookEntity extends BaseEntity {
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
    private List<BookHashTagEntity> bookHashTags = new ArrayList<>();

    @OneToMany
    private List<ReviewEntity> reviews = new ArrayList<>();
}
