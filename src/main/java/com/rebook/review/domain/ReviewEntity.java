package com.rebook.review.domain;

import com.rebook.book.domain.BookEntity;
import com.rebook.common.domain.BaseEntity;
import com.rebook.review.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("리뷰 도서")
    @ManyToOne
    @JoinColumn(name = "bookId")
    private BookEntity book;

    @Comment("리뷰 내용")
    @Column(name = "content", nullable = false)
    private String content;

    @Comment("평점")
    @Column(name = "star_rate", precision = 3, scale = 2)
    private BigDecimal starRate;

    public static ReviewEntity of(BookEntity book, ReviewRequest request) {
        return new ReviewEntity(
                null,
                book,
                request.getContent(),
                request.getStarRate());
    }

}
