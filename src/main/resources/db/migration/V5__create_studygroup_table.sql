CREATE TABLE study_group
(
    id          BIGINT AUTO_INCREMENT      NOT NULL,
    name        VARCHAR(255)               NOT NULL,
    max_members INT                        NOT NULL,
    leader_id   BIGINT                     NOT NULL,
    created_at  datetime(6) DEFAULT NOW(6) NULL,
    updated_at  datetime(6) DEFAULT NOW(6) NULL,
    is_deleted  BIT(1)      DEFAULT 0      NOT NULL,
    deleted_at  datetime(6)                NULL,
    CONSTRAINT pk_study_group PRIMARY KEY (id)
);

ALTER TABLE study_group
    ADD CONSTRAINT FK_STUDY_GROUP_ON_LEADER FOREIGN KEY (leader_id) REFERENCES users (id);