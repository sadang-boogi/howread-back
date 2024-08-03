package com.rebook.reaction.controller.response;

import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
import com.rebook.reaction.service.dto.ReactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReactionResponse {
    private Long Id;
    private Long userId;
    private ReactionType reactionType;
    private TargetType targetType;
    private Long targetId;
    private boolean isOn;

    public static ReactionResponse from(ReactionDto reactionDto) {
        return new ReactionResponse(
                reactionDto.getId(),
                reactionDto.getUserId(),
                reactionDto.getReactionType(),
                reactionDto.getTargetType(),
                reactionDto.getTargetId(),
                reactionDto.isOn()
        );
    }
}
