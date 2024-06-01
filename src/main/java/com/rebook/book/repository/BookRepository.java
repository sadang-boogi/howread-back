package com.rebook.book.repository;

import com.rebook.book.domain.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
            SELECT book FROM BookEntity book
            """)
    Slice<BookEntity> findAllByPageable(Pageable pageable);

    @Query("""
            SELECT DISTINCT book FROM BookEntity book
            WHERE book.id = :bookId
            """)
    Optional<BookEntity> findById(Long bookId);
}
