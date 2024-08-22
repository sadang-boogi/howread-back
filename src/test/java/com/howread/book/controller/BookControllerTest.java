package com.howread.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.howread.auth.interceptor.LoginCheckInterceptor;
import com.howread.book.controller.request.BookCreateRequest;
import com.howread.book.service.BookService;
import com.howread.book.service.command.BookCreateCommand;
import com.howread.book.service.dto.BookDto;
import com.howread.hashtag.service.dto.HashtagDto;
import com.howread.jwt.JwtUtil;
import com.howread.jwt.service.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginCheckInterceptor loginCheckInterceptor;

    @MockBean
    private BookService bookService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtService jwtService;

    private RequestPostProcessor loggedInUser() {
        return request -> {
            request.setAttribute("authClaims", null);
            return request;
        };
    }

    @DisplayName("새로운 책을 저장한다.")
    @Test
    void saveBook() throws Exception {
        // given
        BookCreateRequest request = BookCreateRequest.builder()
                .title("제목")
                .author("저자")
                .thumbnailUrl("책 표지")
                .isbn("9788912345600")
                .hashtagIds(List.of(1L, 2L))
                .build();

        when(loginCheckInterceptor.preHandle(any(), any(), any())).thenReturn(true);

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
                                .with(loggedInUser())
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
                .isbn("9788912345600")
                .hashtagIds(List.of(1L, 2L))
                .build();

        // when, then
        when(loginCheckInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        mockMvc.perform(
                        post("/api/v1/books")
                                .content(objectMapper.writeValueAsString(badRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(loggedInUser())
                                .with(loggedInUser())
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
                .isbn("9788912345600")
                .hashtagIds(List.of(1L, 2L))
                .build();
        // when, then

        when(loginCheckInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        mockMvc.perform(
                        post("/api/v1/books")
                                .content(objectMapper.writeValueAsString(badRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(loggedInUser())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("저자를 입력해주세요."));
    }
}
