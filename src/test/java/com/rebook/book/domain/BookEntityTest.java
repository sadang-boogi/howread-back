package com.rebook.book.domain;

import com.rebook.hashtag.domain.HashtagEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookEntityTest {

    @DisplayName("책에 해시태그를 추가한다.")
    @Test
    void addHashtags() {
        // given
        BookEntity book = BookEntity.of("테스트", "테스트", null);

        // when
        book.addHashtag(HashtagEntity.of("테스트용 해시태그"));

        // then
        assertThat(book.getBookHashtags().get(0).getHashtag())
                .extracting("name").isEqualTo("테스트용 해시태그");
    }

    @DisplayName("책의 해시태그들을 모두 삭제한다.")
    @Test
    void clearHashtags() {
        // given
        BookEntity book = BookEntity.of("테스트", "테스트", null);
        book.addHashtag(HashtagEntity.of("테스트용 해시태그"));

        // when
        book.clearHashtags();

        // then
        assertThat(book.getBookHashtags()).size()
                .isZero();
    }
}