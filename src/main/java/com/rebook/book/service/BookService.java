package com.rebook.book.service;

import com.rebook.book.domain.BookEntity;
import com.rebook.book.repository.BookRepository;
import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.command.BookUpdateCommand;
import com.rebook.book.service.dto.BookDto;
import com.rebook.common.exception.NotFoundException;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.hashtag.repository.HashtagRepository;
import com.rebook.review.domain.ReviewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public Slice<BookDto> getBooks(Pageable pageable) {
        Slice<BookEntity> books = bookRepository.findAllByPageable(pageable);
        return books.map(BookDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public BookDto getBook(final Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        return BookDto.fromEntity(book);
    }

    @Transactional
    public void updateBook(Long bookId, BookUpdateCommand bookUpdateCommand) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        BookEntity updateBookCommand = BookEntity.of(
                bookUpdateCommand.getTitle(),
                bookUpdateCommand.getAuthor(),
                bookUpdateCommand.getThumbnailUrl()
        );

        book.update(updateBookCommand);
        book.clearHashtags();

        if (bookUpdateCommand.getHashtagIds() != null && !bookUpdateCommand.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookUpdateCommand.getHashtagIds());
            hashtags.forEach(book::addHashtag);
        }
    }

    @Transactional
    public void deleteBook(Long bookId) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));
        book.getReviews().forEach(ReviewEntity::softDelete);
        book.softDelete();
    }
}
