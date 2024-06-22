package com.rebook.book.repository;

import com.rebook.book.domain.BookEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("책 엔티티를 조회할 때는 생성일자를 내림차순으로 정렬하여 반환한다.")
    @Test
    void findAllByPageable() {
        // given
        List<BookEntity> bookList = createBookList(30);

        bookRepository.saveAll(bookList);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        // when
        Slice<BookEntity> findAllByPageable = bookRepository.findAllBy(pageable);

        // then
        assertThat(findAllByPageable.getContent()).hasSize(10);
        assertThat(findAllByPageable.getContent().get(0).getCreatedAt())
                .isAfter(findAllByPageable.getContent().get(1).getCreatedAt());
    }

    private static List<BookEntity> createBookList(int saveCount) {
        List<BookEntity> bookList = new ArrayList<>();

        for (int i = 0; i < saveCount; i++) {
            BookEntity book = BookEntity.of(String.format("제목%d", i), String.format("저자%d", i), String.format("썸네일%d", i));
            bookList.add(book);
        }
        return bookList;
    }

}