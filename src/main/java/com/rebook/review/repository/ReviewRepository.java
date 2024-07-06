package com.rebook.review.repository;

import com.rebook.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    @Query("""
            SELECT DISTINCT re FROM ReviewEntity re 
            LEFT JOIN FETCH re.user 
            WHERE re.book.id = :bookId AND re.isDeleted = false 
            ORDER BY re.createdAt ASC
            """)
    List<ReviewEntity> findReviewsByBookId(Long bookId);
    
}
