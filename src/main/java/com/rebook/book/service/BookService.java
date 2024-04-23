package com.rebook.book.service;

import com.rebook.book.domain.entity.BookEntity;
import com.rebook.book.dto.request.BookCreateRequest;
import com.rebook.book.dto.response.BookResponse;
import com.rebook.book.repository.BookRepository;
import com.rebook.common.exception.BadRequestException;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_HASHTAG_ID;

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
            bookCreateRequest.getHashtagIds().forEach(hashtagId -> {
                HashtagEntity hashtag = hashtagRepository.findById(hashtagId)
                        .orElseThrow(() -> new BadRequestException(NOT_FOUND_HASHTAG_ID));
                book.addHashtag(hashtag);
            });
        }

        return BookResponse.of(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooks() {
        List<BookEntity> books = bookRepository.findAll();

        return books.stream()
                .map(BookResponse::of)
                .toList();
    }
}
