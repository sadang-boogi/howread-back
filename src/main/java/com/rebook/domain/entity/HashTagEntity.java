package com.rebook.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "hashtag")
public class HashTagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("해시태그 이름")
    @Column(name = "name", nullable = false)
    private String name;
}
