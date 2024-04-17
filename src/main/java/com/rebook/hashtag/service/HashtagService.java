package com.rebook.hashtag.service;

import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.hashtag.dto.HashtagCreateRequest;
import com.rebook.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public Long create(final HashtagCreateRequest hashtagCreateRequest) {
        HashtagEntity hashtag = HashtagEntity.of(
                hashtagCreateRequest.getName()
        );

        HashtagEntity savedHashtag = hashtagRepository.save(hashtag);
        return savedHashtag.getId();
    }

}

