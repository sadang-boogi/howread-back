package com.howread.hashtag.service;

import com.howread.common.exception.NotFoundException;
import com.howread.hashtag.domain.HashtagEntity;
import com.howread.hashtag.repository.HashtagRepository;
import com.howread.hashtag.service.command.HashtagCommand;
import com.howread.hashtag.service.dto.HashtagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.howread.common.exception.ExceptionCode.NOT_FOUND_HASHTAG_ID;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public HashtagDto create(final HashtagCommand hashtagCommand) {
        HashtagEntity hashtag = HashtagEntity.of(hashtagCommand.getName());
        HashtagEntity createdHashtag = hashtagRepository.save(hashtag);

        return HashtagDto.fromEntity(createdHashtag);
    }

    @Transactional(readOnly = true)
    public List<HashtagDto> getHashtags() {
        List<HashtagEntity> hashtags = hashtagRepository.findAll();

        return hashtags.stream()
                .map(HashtagDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public HashtagDto getHashtag(final Long hashtagId) {
        HashtagEntity hashtag = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG_ID));

        return HashtagDto.fromEntity(hashtag);
    }

    @Transactional
    public void update(final Long hashtagId, final HashtagCommand hashtagUpdateCommand) {
        HashtagEntity hashtagEntity = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG_ID));

        hashtagEntity.changeName(hashtagUpdateCommand.getName());
    }

    @Transactional
    public void softDelete(final Long hashtagId) {
        HashtagEntity hashtagEntity = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_HASHTAG_ID));

        hashtagEntity.softDelete();
    }

}

