package com.howread.book.controller;

import com.howread.auth.annotation.Authenticated;
import com.howread.auth.annotation.LoginRequired;
import com.howread.book.controller.request.BookCreateRequest;
import com.howread.book.controller.request.BookUpdateRequest;
import com.howread.book.controller.response.BookResponse;
import com.howread.book.service.BookService;
import com.howread.book.service.command.BookUpdateCommand;
import com.howread.book.service.dto.BookDto;
import com.howread.common.domain.PageInfo;
import com.howread.common.schema.PageResponse;
import com.howread.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Book", description = "Book API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;

    @LoginRequired
    @Operation(summary = "Create Book", description = "책을 등록한다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @PostMapping
    public ResponseEntity<BookResponse> saveBook(
            @RequestBody @Valid final BookCreateRequest bookCreateRequest
    ) {
        BookResponse book = BookResponse.from(
                bookService.save(bookCreateRequest.toCommand())
        );

        return ResponseEntity.created(URI.create("/api/v1/books/" + book.getId())).body(book);
    }

    @LoginRequired(optional = true)
    @Operation(summary = "Get All Books", description = "등록된 모든 책을 조회한다.")
    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Authenticated AuthClaims authClaims
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<BookDto> books = bookService.getBooks(pageable, authClaims);
        List<BookResponse> items = books.getContent()
                .stream()
                .map(BookResponse::from)
                .toList();

        PageInfo pageInfo = new PageInfo(books.getNumber(), books.getSize(), books.hasNext());

        PageResponse<BookResponse> response = new PageResponse<>(items, pageInfo);

        return ResponseEntity.ok().body(response);
    }

    @LoginRequired(optional = true)
    @Operation(summary = "Get Book", description = "bookId와 일치하는 단일 책을 조회한다.")
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(
            @PathVariable Long bookId,
            @Authenticated AuthClaims authClaims
    ) {
        BookResponse book = BookResponse.from(bookService.getBook(bookId, authClaims));

        return ResponseEntity.ok()
                .body(book);
    }

    @LoginRequired
    @Operation(summary = "Update Book", description = "bookId와 일치하는 책의 정보를 수정한다.")
    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookUpdateRequest bookUpdateRequest
    ) {
        bookService.updateBook(bookId, BookUpdateCommand.from(bookUpdateRequest));
        return ResponseEntity.ok()
                .build();
    }

    //    @LoginRequired
    @Operation(summary = "Delete Book", description = "bookId와 일치하는 책을 삭제한다.")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                .build();
    }
}
