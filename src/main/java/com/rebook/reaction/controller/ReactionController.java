package com.rebook.reaction.controller;

import com.rebook.reaction.controller.response.ReactionResponse;
import com.rebook.reaction.domain.ReactionType;
import com.rebook.reaction.domain.TargetType;
import com.rebook.reaction.service.ReactionService;
import com.rebook.reaction.service.command.ReactionCommand;
import com.rebook.reaction.service.dto.ReactionDto;
import com.rebook.user.service.dto.AuthClaims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/reaction")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    //@LoginRequired
    @PutMapping("/{reactionType}/{targetType}/{targetId}")
    public ResponseEntity<ReactionResponse> turnOnReaction(
            @PathVariable ReactionType reactionType,
            @PathVariable TargetType targetType,
            @PathVariable Long targetId
    ) {
        AuthClaims authClaims = new AuthClaims(1L);
        ReactionCommand command = new ReactionCommand(authClaims.getUserId(), reactionType, targetType, targetId);
        ReactionDto reactionDto = reactionService.on(command);
        ReactionResponse reactionResponse = ReactionResponse.from(reactionDto);
        return ResponseEntity.ok(reactionResponse);
    }

    //@LoginRequired
    @DeleteMapping("/{reactionType}/{targetType}/{targetId}")
    public ResponseEntity<Void> turnOffReaction(
            @PathVariable ReactionType reactionType,
            @PathVariable TargetType targetType,
            @PathVariable Long targetId
    ) {
        AuthClaims authClaims = new AuthClaims(1L);
        ReactionCommand command = new ReactionCommand(authClaims.getUserId(), reactionType, targetType, targetId);
        reactionService.off(command);
        return ResponseEntity.noContent().build();
    }
}
