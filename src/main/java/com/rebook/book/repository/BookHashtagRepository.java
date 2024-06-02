package com.rebook.book.repository;

import com.rebook.book.domain.BookHashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookHashtagRepository extends JpaRepository<BookHashtagEntity, Long> {
    List<BookHashtagEntity> findByBookId(Long bookId);
}
