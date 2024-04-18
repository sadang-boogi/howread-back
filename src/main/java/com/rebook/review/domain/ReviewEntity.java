package com.rebook.review.domain;

import com.rebook.common.domain.BaseEntity;
import com.rebook.review.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Setter
@Getter
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
    @Column(name = "star_rate", precision = 3, scale = 2)
    private BigDecimal starRate;

    public ReviewEntity(Long id, String content, BigDecimal starRate) {
        this.id = id;
        this.content = content;
        this.starRate = starRate;
    }
    public static ReviewEntity of(final ReviewRequest review){
        return new ReviewEntity(null, review.getContent(), review.getStarRate());
    }

    public ReviewEntity() {

    }
}
