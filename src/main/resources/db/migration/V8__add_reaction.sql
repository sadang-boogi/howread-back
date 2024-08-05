CREATE TABLE reaction
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    reaction_type VARCHAR(255)          NOT NULL,
    target_type   VARCHAR(255)          NOT NULL,
    target_id     BIGINT                NOT NULL,
    user_id       BIGINT                NOT NULL,
    CONSTRAINT pk_reaction PRIMARY KEY (id)
);

ALTER TABLE reaction
    ADD CONSTRAINT FK_REACTION_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);
