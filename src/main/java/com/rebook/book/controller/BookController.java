package com.rebook.book.controller;

import com.rebook.book.dto.request.BookCreateRequest;
import com.rebook.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> saveBook(
            @RequestBody @Valid final BookCreateRequest bookCreateRequest
    ) {
        Long bookId = bookService.save(bookCreateRequest);
        return ResponseEntity.created(URI.create("/api/v1/books/" + bookId)).build();
    }

}
