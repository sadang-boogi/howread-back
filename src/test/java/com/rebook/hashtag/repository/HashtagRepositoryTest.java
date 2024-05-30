package com.rebook.hashtag.repository;

import com.rebook.common.domain.BaseEntity;
import com.rebook.hashtag.domain.HashtagEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@DataJpaTest
class HashtagRepositoryTest {

    @Autowired
    private HashtagRepository hashtagRepository;

    @DisplayName("해시태그 아이디들로 해시태그 목록을 조회한다.")
    @Test
    void findByIds() {
        // given
        HashtagEntity hashtag1 = HashtagEntity.of("어려움");
        HashtagEntity hashtag2 = HashtagEntity.of("적절함");

        hashtagRepository.saveAll(List.of(hashtag1, hashtag2));

        // when
        List<HashtagEntity> hashtags = hashtagRepository.findByIds(List.of(1L, 2L));

        // then
        assertThat(hashtags).hasSize(2)
                .extracting("id", "name")
                .containsExactlyInAnyOrder(
                        tuple(1L, "어려움"),
                        tuple(2L, "적절함")
                );
    }

    @DisplayName("해시태그를 모두 삭제한다.")
    @Test
    void deleteAll() {
        // given
        HashtagEntity hashtag1 = HashtagEntity.of("어려움");
        HashtagEntity hashtag2 = HashtagEntity.of("적절함");

        hashtagRepository.saveAll(List.of(hashtag1, hashtag2));
        List<HashtagEntity> hashtags = hashtagRepository.findByIds(List.of(1L, 2L));
        hashtags.forEach(BaseEntity::softDelete);

        // when
        List<HashtagEntity> all = hashtagRepository.findAll();

        // then
        int size = all.size();
        System.out.println("size = " + size);

        for (HashtagEntity hashtag : all) {
            System.out.println("hashtag.getName() = " + hashtag.getName());
        }
    }
}