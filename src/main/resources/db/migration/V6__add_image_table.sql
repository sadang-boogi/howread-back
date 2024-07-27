CREATE TABLE image
(
    id         BIGINT AUTO_INCREMENT      NOT NULL,
    created_at datetime(6) DEFAULT NOW(6) NULL,
    updated_at datetime(6) DEFAULT NOW(6) NULL,
    is_deleted BIT(1)      DEFAULT 0      NOT NULL,
    deleted_at datetime(6)                NULL,
    url        VARCHAR(255)               NOT NULL COMMENT '이미지 주소',
    CONSTRAINT pk_image PRIMARY KEY (id)
);