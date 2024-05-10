package com.rebook.review.domain;

import com.rebook.book.domain.BookEntity;
import com.rebook.common.domain.BaseEntity;
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
@SQLRestriction("is_deleted is false")
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
    @Column(name = "score", precision = 3, scale = 2)
    private BigDecimal score;

    public ReviewEntity(Long id, String content, BigDecimal score) {
        this.id = id;
        this.content = content;
        this.score = score;
    }

    public static ReviewEntity of(BookEntity book, String content, BigDecimal score) {
        return new ReviewEntity(
                null,
                book,
                content,
                score);
    }

    @Override
    public void softDelete() {
        super.softDelete();
    }

}
