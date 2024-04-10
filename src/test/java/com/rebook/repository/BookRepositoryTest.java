package com.rebook.repository;

import com.rebook.domain.entity.BookEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("저장 테스트")
    void saveTest() {
        // given
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle("테스트");
        bookEntity.setAuthor("테스트");
        bookEntity.setThumbnailUrl("썸네일");

        // when
        BookEntity savedBook = bookRepository.save(bookEntity);

        // then
        assertNotNull(savedBook);
        assertEquals(savedBook.getTitle(), bookEntity.getTitle());
        assertEquals(savedBook.getAuthor(), bookEntity.getAuthor());
    }

    @Test
    @DisplayName("조회 테스트")
    void findTest() {
        // given
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle("테스트");
        bookEntity.setAuthor("테스트");
        bookEntity.setThumbnailUrl("썸네일");

        // when
        BookEntity savedBook = bookRepository.save(bookEntity);
        BookEntity foundBook = bookRepository.findById(savedBook.getId()).orElse(null);

        // then
        assertNotNull(foundBook);
        assertEquals(foundBook.getTitle(), bookEntity.getTitle());
        assertEquals(foundBook.getAuthor(), bookEntity.getAuthor());
    }

    @Test
    @DisplayName("책 목록 조회")
    void findAllTest() {
        // given
        BookEntity bookEntity1 = new BookEntity();
        bookEntity1.setTitle("테스트1");
        bookEntity1.setAuthor("테스트1");
        bookEntity1.setThumbnailUrl("썸네일1");

        BookEntity bookEntity2 = new BookEntity();
        bookEntity2.setTitle("테스트2");
        bookEntity2.setAuthor("테스트2");
        bookEntity2.setThumbnailUrl("썸네일2");
        bookRepository.save(bookEntity1);
        bookRepository.save(bookEntity2);

        // when
        List<BookEntity> books = bookRepository.findAll();

        // then
        assertEquals(books.size(), 2);
        assertEquals(books.get(0).getTitle(), bookEntity1.getTitle());
        assertEquals(books.get(1).getTitle(), bookEntity2.getTitle());
    }
}
