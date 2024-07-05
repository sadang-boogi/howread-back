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
        BookEntity book = BookEntity.of("테스트", "테스트", null, "isbn");
        HashtagEntity hashtag = HashtagEntity.of("해시태그1");

        // when
        book.addHashtag(BookHashtagEntity.of(book, hashtag));

        // then
        assertThat(book.getBookHashtags().get(0).getHashtag())
                .extracting("name").isEqualTo("해시태그1");
    }

    @DisplayName("책의 해시태그들을 모두 삭제한다.")
    @Test
    void clearHashtags() {
        // given
        BookEntity book = BookEntity.of("테스트", "테스트", null, "isbn");
        HashtagEntity hashtag = HashtagEntity.of("해시태그1");

        book.addHashtag(BookHashtagEntity.of(book, hashtag));

        // when
        book.clearHashtag();

        // then
        assertThat(book.getBookHashtags()).size()
                .isZero();
    }
}