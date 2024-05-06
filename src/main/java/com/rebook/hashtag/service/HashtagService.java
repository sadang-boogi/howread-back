package com.rebook.hashtag.service;

import com.rebook.common.exception.NotFoundException;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.hashtag.repository.HashtagRepository;
import com.rebook.hashtag.service.command.HashtagCommand;
import com.rebook.hashtag.service.dto.HashtagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_HASHTAG_ID;

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

