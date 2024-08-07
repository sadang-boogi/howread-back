package com.rebook.reaction.service.command;

import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
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
