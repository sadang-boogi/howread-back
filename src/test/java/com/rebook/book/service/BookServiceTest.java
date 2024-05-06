package com.rebook.book.service;

import com.rebook.book.controller.request.BookUpdateRequest;
import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.command.BookUpdateCommand;
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

    @DisplayName("책 제목, 저자, 썸네일 이미지, 해시태그 아이디를 등록 후 등록한 책의 id를 반환한다.")
    @Test
    void saveBookWithoutHashtag() {
        // given
        BookCreateCommand bookCreateCommand = new BookCreateCommand("객사오", "조영호", null, null);

        // when
        BookDto bookDto = bookService.save(bookCreateCommand);

        // then
        assertThat(bookDto.getTitle()).isEqualTo("객사오");
    }

    @DisplayName("책의 제목, 저자, 썸네일 이미지, 해시태그 아이디로 책 정보를 수정한다.")
    @Test
    void updateBook() {
        // given
        BookCreateCommand bookCreateRequest = new BookCreateCommand("객사오", "조영호", null, null);
        BookDto bookDto = bookService.save(bookCreateRequest);

        BookUpdateCommand updateBook = BookUpdateCommand.from(new BookUpdateRequest("수정", "김수용", null, null));

        // when
        bookService.updateBook(bookDto.getId(), updateBook);

        // then
        BookDto book = bookService.getBook(bookDto.getId());
        assertThat(book.getTitle()).isEqualTo("수정");
    }
}