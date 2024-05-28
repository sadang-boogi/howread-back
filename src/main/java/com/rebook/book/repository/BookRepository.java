package com.rebook.book.repository;

import com.rebook.book.domain.BookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("""
            SELECT book FROM BookEntity book
            LEFT JOIN FETCH book.bookHashtags
            WHERE book.isDeleted = false AND book.deletedAt is null
            """)
    Slice<BookEntity> findAllByPageable(Pageable pageable);
}
