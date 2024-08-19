CREATE TABLE refresh_token
(
    id        BIGINT AUTO_INCREMENT                    NOT NULL,
    user_id   BIGINT                                   NOT NULL,
    token     VARCHAR(255)                             NOT NULL,
    issued_at datetime(6) DEFAULT CURRENT_TIMESTAMP(6) NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

ALTER TABLE refresh_token
    ADD CONSTRAINT uc_refresh_token_token UNIQUE (token);
