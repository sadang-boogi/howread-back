package com.howread.reaction.controller.response;

import com.howread.reaction.domain.ReactionType;
import com.howread.reaction.domain.TargetType;
import com.howread.reaction.service.dto.ReactionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReactionResponse {
    private Long id;
    private Long userId;
    private ReactionType reactionType;
    private TargetType targetType;
    private Long targetId;
    private Boolean isOn;

    public static ReactionResponse from(ReactionDto reactionDto) {
        return new ReactionResponse(
                reactionDto.getId(),
                reactionDto.getUserId(),
                reactionDto.getReactionType(),
                reactionDto.getTargetType(),
                reactionDto.getTargetId(),
                reactionDto.getIsOn()
        );
    }
}
