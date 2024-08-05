ALTER TABLE reaction
    ADD is_on BIT(1) NULL;

ALTER TABLE reaction
    MODIFY is_on BIT(1) NOT NULL;

ALTER TABLE reaction
    ADD CONSTRAINT uk_reaction_user_id_reaction_type_target_type_target_id UNIQUE (user_id, reaction_type, target_type, target_id);
