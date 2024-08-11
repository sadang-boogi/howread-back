package com.rebook.book.service.dto;

import com.rebook.reaction.domain.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class BookReactionDto {
    private Long reactionId;
    private Long targetId;
    private ReactionType reactionType;
}
