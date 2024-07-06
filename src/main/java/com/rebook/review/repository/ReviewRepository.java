package com.rebook.review.repository;

import com.rebook.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("""
            SELECT DISTINCT re FROM ReviewEntity re 
            LEFT JOIN FETCH re.user 
            WHERE re.book.id = :bookId AND re.isDeleted = false 
            ORDER BY re.createdAt ASC
            """)
    List<ReviewEntity> findReviewsByBookId(Long bookId);


    @Query("""
            SELECT DISTINCT review From ReviewEntity review
            WHERE review.id = :reviewId
            """)
    Optional<ReviewEntity> findById(Long reviewId);
}
