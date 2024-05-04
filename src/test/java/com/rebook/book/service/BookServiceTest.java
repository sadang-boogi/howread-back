package com.rebook.book.service;

import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.dto.BookDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @DisplayName("책 등록 후 등록한 책의 id를 반환한다.")
    @Test
    void saveBookWithoutHashtag() {
        // given
        BookCreateCommand bookCreateRequest = new BookCreateCommand("객사오", "조영호", null, null);

        // when
        BookDto bookDto = bookService.save(bookCreateRequest);

        // then
        assertThat(bookDto.getTitle()).isEqualTo("객사오");
    }

//    @DisplayName("책 정보를 수정한다.")
//    @Test
//    void updateBook() {
//        // given
//        BookCreateCommand bookCreateRequest = new BookCreateCommand("객사오", "조영호", null, null);
//        BookDto bookDto = bookService.save(bookCreateRequest);
//
//        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest("객객", "김수용", null, null);
//
//        // when
//        bookService.updateBook(bookResponse.getId(), bookUpdateRequest);
//
//        // then
//        BookResponse book = bookService.getBook(bookResponse.getId());
//        assertThat(book.getTitle()).isEqualTo("객객");
//    }
}