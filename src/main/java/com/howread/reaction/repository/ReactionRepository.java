package com.howread.reaction.repository;

import com.howread.reaction.domain.ReactionEntity;
import com.howread.reaction.domain.ReactionType;
import com.howread.reaction.domain.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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

    @Query("""
            SELECT r FROM ReactionEntity r
            WHERE r.user.id = :userId
            AND r.targetId IN :bookIds
            AND r.targetType = 'BOOK'
            AND r.isOn = true
            """)
    List<ReactionEntity> findByUserIdAndBookIds(
            @Param("userId") Long userId,
            @Param("bookIds") List<Long> bookIds
    );

}
