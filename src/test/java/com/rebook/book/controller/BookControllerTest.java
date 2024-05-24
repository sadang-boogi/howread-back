package com.rebook.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebook.book.controller.request.BookCreateRequest;
import com.rebook.book.service.BookService;
import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.dto.BookDto;
import com.rebook.hashtag.service.dto.HashtagDto;
import com.rebook.user.interceptor.JwtInterceptor;
import com.rebook.user.interceptor.LoginInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @MockBean
    private LoginInterceptor loginInterceptor;
    @MockBean
    private JwtInterceptor jwtInterceptor;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;


    @BeforeEach
    void setup() throws Exception {
        //token 인증 부분 mocking
        when(loginInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        when(jwtInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

    @DisplayName("새로운 책을 저장한다.")
    @Test
    void saveBook() throws Exception {
        // given
        BookCreateRequest request = BookCreateRequest.builder()
                .title("제목")
                .author("저자")
                .thumbnailUrl("책 표지")
                .hashtagIds(List.of(1L, 2L))
                .build();

        when(bookService.save(any(BookCreateCommand.class)))
                .thenReturn(BookDto.builder()
                        .id(1L)
                        .title("제목")
                        .author("저자")
                        .thumbnailUrl("책 표지")
                        .rating(BigDecimal.ONE)
                        .hashtags(List.of(HashtagDto.builder()
                                .id(1L)
                                .name("해시태그1").build()))
                        .build());

        // when, then
        mockMvc.perform(
                        post("/api/v1/books")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("책 저장 시 책 제목을 입력하지 않으면 에러가 발생한다.")
    @Test
    void saveBookWithOutTitle() throws Exception {
        // given
        BookCreateRequest badRequest = BookCreateRequest.builder()
                .title(null)
                .author("저자")
                .thumbnailUrl("책 표지")
                .hashtagIds(List.of(1L, 2L))
                .build();

        // when, then
        mockMvc.perform(
                        post("/api/v1/books")
                                .content(objectMapper.writeValueAsString(badRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("책 제목을 입력해주세요."));
    }

    @DisplayName("책 저장 시 책 저자를 입력하지 않으면 에러가 발생한다.")
    @Test
    void saveBookWithOutAuthor() throws Exception {
        // given
        BookCreateRequest badRequest = BookCreateRequest.builder()
                .title("책 제목")
                .author(null)
                .thumbnailUrl("책 표지")
                .hashtagIds(List.of(1L, 2L))
                .build();
        // when, then
        mockMvc.perform(
                        post("/api/v1/books")
                                .content(objectMapper.writeValueAsString(badRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("저자를 입력해주세요."));
    }

    @DisplayName("책 목록을 페이징하여 반환한다.")
    @Test
    void getBooks() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<BookDto> bookDtos = List.of(
                BookDto.builder()
                        .id(1L)
                        .title("제목1")
                        .author("저자1")
                        .thumbnailUrl("책 표지1")
                        .rating(BigDecimal.ONE)
                        .hashtags(List.of(HashtagDto.builder().id(1L).name("해시태그1").build()))
                        .build(),
                BookDto.builder()
                        .id(2L)
                        .title("제목2")
                        .author("저자2")
                        .thumbnailUrl("책 표지2")
                        .rating(BigDecimal.valueOf(2))
                        .hashtags(List.of(HashtagDto.builder().id(2L).name("해시태그2").build()))
                        .build()
        );
        SliceImpl<BookDto> bookSlice = new SliceImpl<>(bookDtos, pageable, false);

        when(bookService.getBooks(any(Pageable.class))).thenReturn(bookSlice);

        // when, then
        mockMvc.perform(get("/api/v1/books")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(1L))
                .andExpect(jsonPath("$.items[0].title").value("제목1"))
                .andExpect(jsonPath("$.items[0].author").value("저자1"))
                .andExpect(jsonPath("$.items[0].thumbnailUrl").value("책 표지1"))
                .andExpect(jsonPath("$.items[0].hashtags[0]").value("해시태그1"))
                .andExpect(jsonPath("$.items[0].averageStarRate").value("1"))
                .andExpect(jsonPath("$.items[1].id").value(2L))
                .andExpect(jsonPath("$.items[1].title").value("제목2"))
                .andExpect(jsonPath("$.items[1].author").value("저자2"))
                .andExpect(jsonPath("$.items[1].thumbnailUrl").value("책 표지2"))
                .andExpect(jsonPath("$.items[1].hashtags[0]").value("해시태그2"))
                .andExpect(jsonPath("$.items[1].averageStarRate").value("2"));
    }
}