package com.howread.reaction.service.command;

import com.howread.reaction.domain.ReactionType;
import com.howread.reaction.domain.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReactionCommand {
    Long userId;
    ReactionType reactionType;
    TargetType targetType;
    Long targetId;
}
