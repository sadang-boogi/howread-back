package com.rebook.book.service;

import com.rebook.book.domain.entity.BookEntity;
import com.rebook.book.dto.request.BookCreateRequest;
import com.rebook.book.dto.request.BookUpdateRequest;
import com.rebook.book.dto.response.BookResponse;
import com.rebook.book.repository.BookRepository;
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
    public BookResponse save(final BookCreateRequest bookCreateRequest) {
        BookEntity book = BookEntity.of(
                bookCreateRequest.getTitle(),
                bookCreateRequest.getAuthor(),
                bookCreateRequest.getThumbnailUrl()
        );

        if (bookCreateRequest.getHashtagIds() != null && !bookCreateRequest.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookCreateRequest.getHashtagIds());
            hashtags.forEach(book::addHashtag);
        }

        return BookResponse.from(bookRepository.save(book));
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

    @Transactional(readOnly = true)
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
}
