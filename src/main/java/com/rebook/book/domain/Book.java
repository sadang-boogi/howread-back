package com.rebook.book.domain;

import com.rebook.review.domain.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Book {

    private final Long id;
    private final String title;
    private final String author;
    private final String thumbnailUrl;
    private final List<Review> reviews;
}
