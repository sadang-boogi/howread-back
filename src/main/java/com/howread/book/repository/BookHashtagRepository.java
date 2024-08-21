package com.howread.book.repository;

import com.howread.book.domain.BookHashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookHashtagRepository extends JpaRepository<BookHashtagEntity, Long> {
    List<BookHashtagEntity> findByBookId(Long bookId);
}
