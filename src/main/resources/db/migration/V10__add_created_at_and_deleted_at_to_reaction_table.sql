ALTER TABLE reaction
    ADD created_at datetime(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL;

ALTER TABLE reaction
    ADD deleted_at datetime(6) DEFAULT CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6) NOT NULL;
