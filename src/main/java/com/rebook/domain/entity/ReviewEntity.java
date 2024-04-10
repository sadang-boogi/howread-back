package com.rebook.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Table(name = "review")
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("리뷰 내용")
    @Column(name = "content", nullable = false)
    private String content;

    @Comment("평점")
    @Column(name = "star_rate", precision = 1, scale = 2)
    private BigDecimal starRate;
}
