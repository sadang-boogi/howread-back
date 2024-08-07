package com.rebook.reaction.service.dto;

import com.rebook.reaction.domain.ReactionEntity;
import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReactionDto {
    private Long id;
    private Long userId;
    private ReactionType reactionType;
    private TargetType targetType;
    private Long targetId;
    private Boolean isOn;

    public static ReactionDto from(ReactionEntity reactionEntity) {
        return new ReactionDto(
                reactionEntity.getId(),
                reactionEntity.getUser().getId(),
                reactionEntity.getReactionType(),
                reactionEntity.getTargetType(),
                reactionEntity.getTargetId(),
                reactionEntity.getIsOn()
        );
    }
}
