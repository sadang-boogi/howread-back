# 책 리뷰

## Domain Class, ER Diagram -> JPA Settings 셋팅

## Diagram

### 1. Class Diagram

domain
- models -> Book, Review, HashTag (비즈니스 객체) (논리적)
- entities -> BookEntity, ReviewEntity, HashTagEntity (Persistency Layer) (물리적)

Book -> HashTags[]

```mermaid
---
title: 클래스 다이어그램
---
classDiagram
    Book --> Review: composition
    Book --> HashTag: composition

    class Book {
        Long id
        String title
        String author
        String thumnail_url
        List~Review~ reviews
        List~HashTag~ hashtags
        addReview()
        addHashTag()
        calculateStarRate()
    }
    class Review {
        Long id
        String content
        BigDecimal starRate "1.0~5.0 사이 값"
    }
    class HashTag {
        Long id
        String content
    }
```

### 2. ER Diagram

```mermaid
erDiagram
    BookEntity {
        Long id
        String name
        String author
        String thumbnail_url
        datetime created_at
        datetime updated_at
    }
    BookHashTagEntity {
        Long id
        Long bookId
        Long hashtagId
    }
    ReviewEntity {
        Long id
        String content
        datetime created_at
        datetime updated_at
    }
    HashTagEntity {
        Long id
        String content
        datetime created_at
        datetime updated_at
    }
```

# UseCase

## 1. 책을 등록한다.

- 해시태그도 별도 독립적인 객체
  책 -> 해시태그를 등록하라.

book = Book()
hashtag = HashTag()
book.addHashTag(hashTag)

# Book

# HashTag

## 2. 리뷰를 등록한다.

- 리뷰는 리뷰를 작성할 책임이 있다.
- 책한테 리뷰를 추가할 책임이 있다..

```java
Review review = Review();

Book book = Book();
book.

addReview(review);
```

- book
- reviews

- 책은 평점을 계산할 책임을 가지고있다.

```java
BigDecimal starRate = book.calculateStarRate();
```

