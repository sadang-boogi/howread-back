package com.rebook.book.service;

import com.rebook.book.domain.BookEntity;
import com.rebook.book.domain.BookHashtagEntity;
import com.rebook.book.repository.BookHashtagRepository;
import com.rebook.book.repository.BookRepository;
import com.rebook.book.service.command.BookCreateCommand;
import com.rebook.book.service.command.BookUpdateCommand;
import com.rebook.book.service.dto.BookDto;
import com.rebook.book.service.dto.BookReactionDto;
import com.rebook.common.domain.BaseEntity;
import com.rebook.common.exception.NotFoundException;
import com.rebook.hashtag.domain.HashtagEntity;
import com.rebook.hashtag.repository.HashtagRepository;
import com.rebook.reaction.domain.ReactionEntity;
import com.rebook.reaction.repository.ReactionRepository;
import com.rebook.review.domain.ReviewEntity;
import com.rebook.user.service.dto.AuthClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rebook.common.exception.ExceptionCode.NOT_FOUND_BOOK_ID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReactionRepository reactionRepository;
    private final BookHashtagRepository bookHashtagRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public BookDto save(final BookCreateCommand bookCreateCommand) {
        BookEntity book = BookEntity.of(bookCreateCommand.getTitle(), bookCreateCommand.getAuthor(), bookCreateCommand.getThumbnailUrl(), bookCreateCommand.getIsbn());

        if (bookCreateCommand.getHashtagIds() != null && !bookCreateCommand.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookCreateCommand.getHashtagIds());
            setHashtag(hashtags, book);
        }

        return BookDto.from(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public Slice<BookDto> getBooks(Pageable pageable, AuthClaims authClaims) {

        // 1. 책 목록 조회
        Slice<BookEntity> bookEntities = bookRepository.findAllBy(pageable);
        List<Long> bookIds = bookEntities.getContent().stream()
                .map(BookEntity::getId)
                .toList();

        // 2. 기본 BookDto 리스트
        List<BookDto> bookDtos = bookEntities.getContent().stream().map(BookDto::from).toList();

        // 3. 리액션 추가
        if (authClaims != null && !bookIds.isEmpty()) {
            List<ReactionEntity> reactions = reactionRepository.findByUserIdAndBookIds(authClaims.getUserId(), bookIds);

            // bookId가 키인 BookReactionDto Map
            Map<Long, List<BookReactionDto>> reactionMap = reactions.stream().map(reaction -> new BookReactionDto(reaction.getId(), reaction.getTargetId(), reaction.getReactionType())).collect(Collectors.groupingBy(BookReactionDto::getTargetId));

            // BookDto에 리액션 추가
            for (BookDto bookDto : bookDtos) {
                List<BookReactionDto> bookReactions = reactionMap.get(bookDto.getId());
                if (bookReactions != null) {
                    bookReactions.forEach(bookDto::addReaction);
                }
            }
        }
        return new SliceImpl<>(bookDtos, pageable, bookEntities.hasNext());
    }

    @Transactional(readOnly = true)
    public BookDto getBook(final Long bookId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        return BookDto.from(book);
    }

    @Transactional
    public void updateBook(Long bookId, BookUpdateCommand bookUpdateCommand) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        BookEntity updateBook = BookEntity.builder().title(bookUpdateCommand.getTitle()).author(bookUpdateCommand.getAuthor()).thumbnailUrl(bookUpdateCommand.getThumbnailUrl()).isbn(bookUpdateCommand.getIsbn()).build();

        book.update(updateBook);

        List<BookHashtagEntity> findBookHashtags = bookHashtagRepository.findByBookId(bookId);
        findBookHashtags.forEach(BaseEntity::softDelete);

        if (bookUpdateCommand.getHashtagIds() != null && !bookUpdateCommand.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookUpdateCommand.getHashtagIds());
            setHashtag(hashtags, book);
        }
    }

    @Transactional
    public void deleteBook(Long bookId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));
        book.getReviews().forEach(ReviewEntity::softDelete);
        book.softDelete();
        book.getBookHashtags().forEach(BaseEntity::softDelete);
    }

    private void setHashtag(List<HashtagEntity> hashtags, BookEntity book) {
        for (HashtagEntity hashtag : hashtags) {
            BookHashtagEntity bookHashtag = BookHashtagEntity.of(book, hashtag);
            book.addHashtag(bookHashtag);
        }
    }
}
