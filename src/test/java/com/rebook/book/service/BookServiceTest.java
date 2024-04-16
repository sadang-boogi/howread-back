package com.rebook.book.service;

import com.rebook.book.dto.request.BookCreateRequest;
import com.rebook.book.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Transactional
class BookServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @DisplayName("책 등록 후 등록한 책의 id를 반환한다.")
    @Test
    void saveBookWithoutHashtag() {
        // given
        BookCreateRequest bookCreateRequest = new BookCreateRequest("객사오", "조영호", null);

        // when
        Long bookId = bookService.save(bookCreateRequest);

        // then
        assertThat(bookId).isEqualTo(0);
    }
}