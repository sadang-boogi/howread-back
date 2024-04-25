package com.rebook.hashtag.repository;

import com.rebook.hashtag.domain.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {

    List<HashtagEntity> findByIds(List<Long> hashtagIds);
}
