package com.rebook.user.controller;

import com.rebook.jwt.JwtUtil;
import com.rebook.jwt.service.JwtService;
import com.rebook.jwt.service.RefreshTokenService;
import com.rebook.user.controller.request.SocialLoginRequest;
import com.rebook.user.controller.response.JwtResponse;
import com.rebook.user.controller.response.UriResponse;
import com.rebook.user.service.LoginService;
import com.rebook.user.service.dto.AuthClaims;
import com.rebook.user.util.SocialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/login/oauth", produces = "application/json")
public class LoginController {
    private final LoginService loginService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Operation(
            summary = "OAuth URL 생성",
            description = "OAuth 인증을 위한 URL을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OAuth 인증 URL을 반환합니다.")
            }
    )
    @GetMapping("/{provider}/authorize")
    public ResponseEntity<UriResponse> getAuthorizationUrl(
            @PathVariable SocialType provider,
            @RequestParam String redirectUri
    ) {
        String authorizationUrl = loginService.getAuthorizationUrl(provider, redirectUri);
        return ResponseEntity.ok(new UriResponse(authorizationUrl));
    }

    @Operation(
            summary = "소셜 로그인",
            description = "소셜 프로바이더를 통해 로그인하고 JWT 토큰을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "JWT 토큰을 반환합니다.")
            }
    )
    @PostMapping("/{provider}")
    public ResponseEntity<JwtResponse> socialLogin(
            @RequestBody SocialLoginRequest body,
            @PathVariable SocialType provider
    ) {
        AuthClaims auth = loginService.socialLogin(body.getCode(), provider, body.getRedirectUri());
        String accessToken = jwtUtil.createAccessToken(auth);
        String refreshToken = jwtUtil.createRefreshToken(auth);

        refreshTokenService.saveRefreshToken(refreshToken, auth.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(accessToken, accessToken, refreshToken));
    }

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

        return ResponseEntity.status(HttpStatus.CREATED).body(new JwtResponse(newAccessToken, newAccessToken, refreshToken));
    }
}
