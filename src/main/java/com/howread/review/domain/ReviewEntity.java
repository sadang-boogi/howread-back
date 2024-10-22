package com.howread.review.domain;

import com.howread.book.domain.BookEntity;
import com.howread.common.domain.BaseEntity;
import com.howread.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "review")
@AllArgsConstructor
@SQLRestriction("is_deleted = false")
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("리뷰 도서")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Comment("리뷰 내용")
    @Column(name = "content", nullable = false)
    private String content;

    @Comment("평점")
    @Column(name = "score", precision = 3, scale = 2)
    private BigDecimal score;

    public ReviewEntity(BookEntity book, UserEntity user, String content, BigDecimal score) {
        this.book = book;
        this.user = user;
        this.content = content;
        this.score = score;
    }

    public static ReviewEntity of(BookEntity book, UserEntity user, String content, BigDecimal score) {
        ReviewEntity review = new ReviewEntity(book, user, content, score);
        user.addReview(review);  // 리뷰 생성시 user의 review 업데이트
        return review;
    }

    public ReviewEntity update(BookEntity book, String content, BigDecimal score) {
        this.book = book;
        this.content = content;
        this.score = score;
        return this;
    }
}
