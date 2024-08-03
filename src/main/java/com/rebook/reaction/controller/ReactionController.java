package com.rebook.reaction.controller;


import com.rebook.auth.annotation.Authenticated;
import com.rebook.auth.annotation.LoginRequired;
import com.rebook.reaction.service.command.ReactionSaveCommand;
import com.rebook.reaction.controller.request.ReactionSaveRequest;
import com.rebook.reaction.controller.response.ReactionResponse;
import com.rebook.reaction.service.ReactionService;
import com.rebook.reaction.service.dto.ReactionDto;
import com.rebook.user.service.dto.AuthClaims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user/reaction")
@RestController
public class ReactionController {

    private final ReactionService reactionService;

    @LoginRequired
    @PostMapping
    public ResponseEntity<ReactionResponse> createResponse(
            @RequestBody @Valid final ReactionSaveRequest reactionRequest, @Authenticated AuthClaims authClaims
    ) {
        ReactionSaveCommand command = reactionRequest.toCommand(authClaims.getUserId());
        ReactionDto reactionDto = reactionService.save(command);
        ReactionResponse reactionResponse = ReactionResponse.from(reactionDto);
        return ResponseEntity.created(URI.create("/api/v1/user/reaction/" + reactionResponse.getId())).body(reactionResponse);
    }
    @LoginRequired
    @GetMapping
    public ResponseEntity<ReactionResponse> getResponse(Long reactionId
    ) {
        ReactionDto reactionDto = reactionService.getReaction(reactionId);
        ReactionResponse reactionResponse = ReactionResponse.from(reactionDto);
        return ResponseEntity.created(URI.create("/api/v1/user/reaction/" + reactionResponse.getId())).body(reactionResponse);
    }

}
