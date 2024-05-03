package com.rebook.book.service;

import com.rebook.book.controller.request.BookUpdateRequest;
import com.rebook.book.controller.response.BookResponse;
import com.rebook.book.domain.BookEntity;
import com.rebook.book.repository.BookRepository;
import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.dto.BookDto;
import com.rebook.common.exception.NotFoundException;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_BOOK_ID;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public BookDto save(final BookCreateCommand bookCreateCommand) {
        BookEntity book = BookEntity.of(
                bookCreateCommand.getTitle(),
                bookCreateCommand.getAuthor(),
                bookCreateCommand.getThumbnailUrl()
        );

        if (bookCreateCommand.getHashtagIds() != null && !bookCreateCommand.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookCreateCommand.getHashtagIds());
            hashtags.forEach(book::addHashtag);
        }

        return BookDto.fromEntity(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooks() {
        List<BookEntity> books = bookRepository.findAll();

        return books.stream()
                .map(BookResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponse getBook(final Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        return BookResponse.from(book);
    }

    @Transactional
    public void updateBook(Long bookId, BookUpdateRequest bookUpdateRequest) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        book.update(bookUpdateRequest);
        book.clearHashTags();

        if (bookUpdateRequest.getHashtagIds() != null && !bookUpdateRequest.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookUpdateRequest.getHashtagIds());
            hashtags.forEach(book::addHashtag);
        }
    }

    @Transactional
    public void deleteBook(Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));
        book.softDelete();
    }
}
