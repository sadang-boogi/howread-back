package com.rebook.review.repository;

import com.rebook.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>{
    List<ReviewEntity> findAllByOrderByCreatedAtDesc();
}
