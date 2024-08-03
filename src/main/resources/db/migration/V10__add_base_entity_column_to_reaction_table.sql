ALTER TABLE rebook_dev.reaction
    ADD created_at datetime(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL,
    ADD updated_at datetime(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) NOT NULL,
    ADD is_deleted BIT(1) DEFAULT 0 NOT NULL,
    ADD deleted_at datetime(6) NULL;
