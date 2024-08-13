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
import com.rebook.reaction.domain.ReactionType;
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

import java.util.ArrayList;
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
        BookEntity book = BookEntity.of(
                bookCreateCommand.getTitle(),
                bookCreateCommand.getAuthor(),
                bookCreateCommand.getThumbnailUrl(),
                bookCreateCommand.getIsbn());

        if (bookCreateCommand.getHashtagIds() != null && !bookCreateCommand.getHashtagIds().isEmpty()) {
            List<HashtagEntity> hashtags = hashtagRepository.findByIds(bookCreateCommand.getHashtagIds());
            setHashtag(hashtags, book);
        }

        return BookDto.from(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public Slice<BookDto> getBooks(Pageable pageable, AuthClaims authClaims) {

        // 1. 책 목록 조회 및 BookDto 변환
        Slice<BookEntity> bookEntities = bookRepository.findAllBy(pageable);
        List<Long> bookIds = bookEntities.getContent().stream()
                .map(BookEntity::getId)
                .toList();

        Map<Long, BookDto> bookDtos = bookEntities.getContent().stream()
                .map(BookDto::from)
                .collect(Collectors.toMap(BookDto::getId, bookDto -> bookDto));

        // 2. 리액션 추가
        if (authClaims != null && !bookIds.isEmpty()) {
            addReactionsToBooks(authClaims.getUserId(), bookIds, bookDtos);
        }

        return new SliceImpl<>(new ArrayList<>(bookDtos.values()), pageable, bookEntities.hasNext());
    }

    private void addReactionsToBooks(Long userId, List<Long> bookIds, Map<Long, BookDto> bookDtos) {
        List<ReactionEntity> reactions = reactionRepository.findByUserIdAndBookIds(userId, bookIds);

        reactions.forEach(reaction -> {
            BookDto bookDto = bookDtos.get(reaction.getTargetId());
            BookReactionDto reactionDto = bookDto.getReaction();
            switch (reaction.getReactionType()) {
                case FOLLOW -> reactionDto.setFollowedByMe(true);
                case LIKE -> reactionDto.setLikedByMe(true);
            }
        });

    }


    @Transactional(readOnly = true)
    public BookDto getBook(final Long bookId, AuthClaims authClaims) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        BookReactionDto reactionDto = null;

        if (authClaims != null) {
            // 사용자의 리액션 조회
            ReactionEntity reactionEntity = reactionRepository.findByUserIdAndBookId(authClaims.getUserId(), bookId);

            if (reactionEntity != null) {
                reactionDto = new BookReactionDto(
                        reactionEntity.getReactionType().equals(ReactionType.FOLLOW), // 사용자가 팔로우했는지 여부
                        reactionEntity.getReactionType().equals(ReactionType.LIKE)    // 사용자가 좋아요를 눌렀는지 여부
                );
            } else {
                // 리액션 정보가 없으면 기본값 설정
                reactionDto = new BookReactionDto(false, false);
            }
        }

        return BookDto.from(book, reactionDto);
    }

    @Transactional
    public void updateBook(Long bookId, BookUpdateCommand bookUpdateCommand) {
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));

        BookEntity updateBook = BookEntity.builder()
                .title(bookUpdateCommand.getTitle())
                .author(bookUpdateCommand.getAuthor())
                .thumbnailUrl(bookUpdateCommand.getThumbnailUrl())
                .isbn(bookUpdateCommand.getIsbn())
                .build();
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
        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BOOK_ID));
        book.getReviews()
                .forEach(ReviewEntity::softDelete);
        book.softDelete();
        book.getBookHashtags()
                .forEach(BaseEntity::softDelete);
    }

    private void setHashtag(List<HashtagEntity> hashtags, BookEntity book) {
        for (HashtagEntity hashtag : hashtags) {
            BookHashtagEntity bookHashtag = BookHashtagEntity.of(book, hashtag);
            book.addHashtag(bookHashtag);
        }
    }
}
