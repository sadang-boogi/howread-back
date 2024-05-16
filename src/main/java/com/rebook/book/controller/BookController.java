package com.rebook.book.controller;

import com.rebook.book.controller.request.BookCreateRequest;
import com.rebook.book.controller.request.BookUpdateRequest;
import com.rebook.book.controller.response.BookResponse;
import com.rebook.book.domain.BookEntity;
import com.rebook.book.repository.BookRepository;
import com.rebook.book.service.BookService;
import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.command.BookUpdateCommand;
import com.rebook.book.service.dto.BookDto;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction;

@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(
            @RequestBody @Valid final BookCreateRequest bookCreateRequest
    ) {
        BookResponse book = BookResponse.from(bookService.save(BookCreateCommand.from(bookCreateRequest)));

        return ResponseEntity.created(URI.create("/api/v1/books/" + book.getId())).body(book);
    }

    @GetMapping
    public ResponseEntity<Page<BookDto>> getBooks(
            @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        Page<BookDto> books = bookService.getBooks(pageable);

        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long bookId) {
        BookResponse book = BookResponse.from(bookService.getBook(bookId));

        return ResponseEntity.ok()
                .body(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookUpdateRequest bookUpdateRequest
    ) {
        bookService.updateBook(bookId, BookUpdateCommand.from(bookUpdateRequest));
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok()
                .build();
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            bookRepository.save(BookEntity.of("book" + i, "author" + i, "thumbnail" + i));
        }
    }
}
