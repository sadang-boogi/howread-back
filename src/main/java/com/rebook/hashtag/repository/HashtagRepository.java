package com.rebook.hashtag.repository;

import com.rebook.hashtag.domain.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {
    @Query("SELECT h FROM HashtagEntity h WHERE h.id IN :hashtagIds")
    List<HashtagEntity> findByIds(List<Long> hashtagIds);
}
