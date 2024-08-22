package com.howread.jwt.controller;

import com.howread.jwt.JwtUtil;
import com.howread.jwt.service.JwtService;
import com.howread.user.controller.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/token", produces = "application/json")
@RestController
public class TokenController {

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Operation(
            summary = "액세스 토큰 갱신",
            description = "리프레시 토큰을 통해 액세스 토큰을 갱신합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "JWT 토큰을 반환합니다.")
            }
    )
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshAccessToken(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String refreshToken = jwtUtil.extractTokenFromHeader(authorizationHeader);
        String newAccessToken = jwtService.refreshAccessToken(refreshToken);

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(newAccessToken)
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponse);
    }
}
