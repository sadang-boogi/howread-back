package com.rebook.book.controller;

import com.rebook.auth.annotation.LoginRequired;
import com.rebook.book.controller.request.BookCreateRequest;
import com.rebook.book.controller.request.BookUpdateRequest;
import com.rebook.book.controller.response.BookResponse;
import com.rebook.book.service.BookService;
import com.rebook.book.service.command.BookUpdateCommand;
import com.rebook.book.service.dto.BookDto;
import com.rebook.common.domain.PageInfo;
import com.rebook.common.schema.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Operation(summary = "Get All Books", description = "등록된 모든 책을 조회한다.")

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> getBooks(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Slice<BookDto> books = bookService.getBooks(pageable);
        List<BookResponse> items = books.getContent()
                .stream()
                .map(BookResponse::from)
                .toList();

        PageInfo pageInfo = new PageInfo(books.getNumber(), books.getSize(), books.hasNext());

        PageResponse<BookResponse> response = new PageResponse<>(items, pageInfo);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get Book", description = "bookId와 일치하는 단일 책을 조회한다.")
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long bookId) {
        BookResponse book = BookResponse.from(bookService.getBook(bookId));

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

    @Operation(summary = "Delete Book", description = "bookId와 일치하는 책을 삭제한다.")
    @LoginRequired
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                .build();
    }
}
