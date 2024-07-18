CREATE TABLE study_group
(
    id          BIGINT AUTO_INCREMENT      NOT NULL,
    name        VARCHAR(255)               NOT NULL,
    description VARCHAR(255)               NULL,
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

CREATE TABLE study_group_member
(
    id             BIGINT AUTO_INCREMENT      NOT NULL,
    study_group_id BIGINT                     NOT NULL,
    user_id        BIGINT                     NOT NULL,
    created_at     datetime(6) DEFAULT NOW(6) NULL,
    updated_at     datetime(6) DEFAULT NOW(6) NULL,
    is_deleted     BIT(1)      DEFAULT 0      NOT NULL,
    deleted_at     datetime(6)                NULL,
    CONSTRAINT pk_study_group_member PRIMARY KEY (id)
);

ALTER TABLE study_group_member
    ADD CONSTRAINT FK_STUDY_GROUP_MEMBER_ON_STUDY_GROUP FOREIGN KEY (study_group_id) REFERENCES study_group (id);

ALTER TABLE study_group_member
    ADD CONSTRAINT FK_STUDY_GROUP_MEMBER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);