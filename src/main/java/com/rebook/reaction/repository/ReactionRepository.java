package com.rebook.reaction.repository;

import com.rebook.reaction.domain.ReactionEntity;
import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

    @Query("""
            SELECT r FROM ReactionEntity r
            WHERE r.user.id = :userId
            AND r.reactionType = :reactionType
            AND r.targetType = :targetType
            AND r.targetId = :targetId
            """)
    Optional<ReactionEntity> findReaction(
            @Param("userId") Long userId,
            @Param("reactionType") ReactionType reactionType,
            @Param("targetType") TargetType targetType,
            @Param("targetId") Long targetId
    );

}
