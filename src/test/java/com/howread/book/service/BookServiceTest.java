package com.howread.book.service;

import com.howread.book.controller.request.BookUpdateRequest;
import com.howread.book.service.command.BookCreateCommand;
import com.howread.book.service.command.BookUpdateCommand;
import com.howread.book.service.dto.BookDto;
import com.howread.hashtag.controller.requeest.HashtagRequest;
import com.howread.hashtag.service.HashtagService;
import com.howread.hashtag.service.command.HashtagCommand;
import com.howread.user.service.dto.AuthClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private HashtagService hashtagService;

    @BeforeEach
    void setup() {
        HashtagCommand hashtagCommand1 = HashtagCommand.of(new HashtagRequest("해시태그1"));
        HashtagCommand hashtagCommand2 = HashtagCommand.of(new HashtagRequest("해시태그2"));
        HashtagCommand hashtagCommand3 = HashtagCommand.of(new HashtagRequest("해시태그3"));

        hashtagService.create(hashtagCommand1);
        hashtagService.create(hashtagCommand2);
        hashtagService.create(hashtagCommand3);
    }

    @DirtiesContext
    @DisplayName("책 제목, 저자, 썸네일 이미지, 해시태그 아이디를 등록 후 등록한 책의 id를 반환한다.")
    @Test
    void saveBookWithoutHashtag() {
        // given
        BookCreateCommand bookCreateCommand = new BookCreateCommand("객사오", "조영호", "썸네일1", "9788912345600", List.of(1L, 2L, 3L));

        // when
        BookDto bookDto = bookService.save(bookCreateCommand);

        // then
        assertThat(bookDto.getTitle()).isEqualTo("객사오");
        assertThat(bookDto.getHashtags().size()).isEqualTo(3);
    }

    @DirtiesContext
    @DisplayName("책의 제목, 저자, 썸네일 이미지, 해시태그 아이디로 책 정보를 수정한다.")
    @Test
    void updateBook() {
        // given
        BookCreateCommand bookCreateRequest = new BookCreateCommand("제목1", "저자1", "썸네일1", "9788912345600", List.of(1L, 2L, 3L));
        BookDto bookDto = bookService.save(bookCreateRequest);

        BookUpdateCommand updateBook = BookUpdateCommand.from(new BookUpdateRequest("수정제목", "수정저자", "수정썸네일", "9788912345700", List.of(1L)));

        // when
        bookService.updateBook(bookDto.getId(), updateBook);

        // then
        BookDto book = bookService.getBook(bookDto.getId(),new AuthClaims(1L));
        assertThat(book.getTitle()).isEqualTo("수정제목");
        assertThat(book.getAuthor()).isEqualTo("수정저자");
        assertThat(book.getThumbnailUrl()).isEqualTo("수정썸네일");
        assertThat(book.getIsbn()).isEqualTo("9788912345700");
    }
}
