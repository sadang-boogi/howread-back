ALTER TABLE rebook_dev.users
    ADD CONSTRAINT uk_user_social_type_social_id UNIQUE (social_type, social_id);
