package com.rebook.book.controller;

import com.rebook.book.controller.request.BookCreateRequest;
import com.rebook.book.controller.request.BookUpdateRequest;
import com.rebook.book.controller.response.BookResponse;
import com.rebook.book.service.BookService;
import com.rebook.common.schema.ListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(
            @RequestBody @Valid final BookCreateRequest bookCreateRequest
    ) {
        BookResponse book = bookService.save(bookCreateRequest);
        return ResponseEntity.created(URI.create("/api/v1/books/" + book.getId())).body(book);
    }

    @GetMapping
    public ResponseEntity<ListResponse<BookResponse>> getBooks() {
        final List<BookResponse> books = bookService.getBooks();
        ListResponse<BookResponse> response = new ListResponse<>(books);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long bookId) {
        BookResponse book = bookService.getBook(bookId);

        return ResponseEntity.ok()
                .body(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookUpdateRequest bookUpdateRequest
    ) {
        bookService.updateBook(bookId, bookUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                .build();
    }
}
