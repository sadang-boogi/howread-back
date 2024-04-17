package com.rebook.hashtag.repository;

import com.rebook.hashtag.domain.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {
}
