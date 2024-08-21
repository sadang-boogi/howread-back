package com.howread.reaction.controller;

import com.howread.auth.annotation.Authenticated;
import com.howread.auth.annotation.LoginRequired;
import com.howread.reaction.controller.response.ReactionResponse;
import com.howread.reaction.domain.ReactionType;
import com.howread.reaction.domain.TargetType;
import com.howread.reaction.service.ReactionService;
import com.howread.reaction.service.command.ReactionCommand;
import com.howread.reaction.service.dto.ReactionDto;
import com.howread.user.service.dto.AuthClaims;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/reaction")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @LoginRequired
    @PutMapping("/{reactionType}/{targetType}/{targetId}")
    public ResponseEntity<ReactionResponse> turnOnReaction(
            @PathVariable ReactionType reactionType,
            @PathVariable TargetType targetType,
            @PathVariable Long targetId,
            @Parameter(hidden = true) @Authenticated AuthClaims authClaims
    ) {
        ReactionCommand command = new ReactionCommand(authClaims.getUserId(), reactionType, targetType, targetId);
        ReactionDto reactionDto = reactionService.on(command);
        ReactionResponse reactionResponse = ReactionResponse.from(reactionDto);
        return ResponseEntity.ok(reactionResponse);
    }

    @LoginRequired
    @DeleteMapping("/{reactionType}/{targetType}/{targetId}")
    public ResponseEntity<Void> turnOffReaction(
            @PathVariable ReactionType reactionType,
            @PathVariable TargetType targetType,
            @PathVariable Long targetId,
            @Parameter(hidden = true) @Authenticated AuthClaims authClaims
    ) {
        ReactionCommand command = new ReactionCommand(authClaims.getUserId(), reactionType, targetType, targetId);
        reactionService.off(command);
        return ResponseEntity.noContent().build();
    }
}
