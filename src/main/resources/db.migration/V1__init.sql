CREATE TABLE book
(
    id            BIGINT AUTO_INCREMENT                     NOT NULL,
    created_at    datetime(6) DEFAULT NOW()                 NULL,
    updated_at    datetime(6) DEFAULT now() on update now() NULL,
    is_deleted    BIT(1)      DEFAULT 0                     NOT NULL,
    deleted_at    datetime(6)                               NULL,
    title         VARCHAR(255)                              NOT NULL,
    author        VARCHAR(255)                              NOT NULL,
    thumbnail_url VARCHAR(255)                              NULL COMMENT '책 대표 표지 이미지',
    CONSTRAINT pk_book PRIMARY KEY (id)
);

CREATE TABLE book_hashtag
(
    id         BIGINT AUTO_INCREMENT                     NOT NULL,
    created_at datetime(6) DEFAULT NOW()                 NULL,
    updated_at datetime(6) DEFAULT now() on update now() NULL,
    is_deleted BIT(1)      DEFAULT 0                     NOT NULL,
    deleted_at datetime(6)                               NULL,
    book_id    BIGINT                                    NULL,
    hashtag_id BIGINT                                    NULL,
    CONSTRAINT pk_book_hashtag PRIMARY KEY (id)
);

CREATE TABLE hashtag
(
    id         BIGINT AUTO_INCREMENT                     NOT NULL,
    created_at datetime(6) DEFAULT NOW()                 NULL,
    updated_at datetime(6) DEFAULT now() on update now() NULL,
    is_deleted BIT(1)      DEFAULT 0                     NOT NULL,
    deleted_at datetime(6)                               NULL,
    name       VARCHAR(255)                              NOT NULL COMMENT '해시태그 이름',
    CONSTRAINT pk_hashtag PRIMARY KEY (id)
);

CREATE TABLE review
(
    id         BIGINT AUTO_INCREMENT                     NOT NULL,
    created_at datetime(6) DEFAULT NOW()                 NULL,
    updated_at datetime(6) DEFAULT now() on update now() NULL,
    is_deleted BIT(1)      DEFAULT 0                     NOT NULL,
    deleted_at datetime(6)                               NULL,
    book_id    BIGINT                                    NULL COMMENT '리뷰 도서',
    user_id    BIGINT                                    NOT NULL,
    content    VARCHAR(255)                              NOT NULL COMMENT '리뷰 내용',
    score      DECIMAL(3, 2)                             NULL COMMENT '평점',
    CONSTRAINT pk_review PRIMARY KEY (id)
);

CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT                     NOT NULL,
    created_at  datetime(6) DEFAULT NOW()                 NULL,
    updated_at  datetime(6) DEFAULT now() on update now() NULL,
    is_deleted  BIT(1)      DEFAULT 0                     NOT NULL,
    deleted_at  datetime(6)                               NULL,
    nickname    VARCHAR(255)                              NOT NULL,
    email       VARCHAR(255)                              NOT NULL,
    `role`      VARCHAR(255)                              NOT NULL,
    social_type VARCHAR(255)                              NULL,
    social_id   VARCHAR(255)                              NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE book_hashtag
    ADD CONSTRAINT FK_BOOK_HASHTAG_ON_BOOK FOREIGN KEY (book_id) REFERENCES book (id);

ALTER TABLE book_hashtag
    ADD CONSTRAINT FK_BOOK_HASHTAG_ON_HASHTAG FOREIGN KEY (hashtag_id) REFERENCES hashtag (id);

ALTER TABLE review
    ADD CONSTRAINT FK_REVIEW_ON_BOOKID FOREIGN KEY (book_id) REFERENCES book (id);
