package com.rebook.user.controller;

import com.rebook.user.controller.response.JwtResponse;
import com.rebook.user.controller.response.UriResponse;
import com.rebook.user.service.LoginService;
import com.rebook.jwt.JwtUtil;
import com.rebook.user.service.dto.AuthClaims;
import com.rebook.user.util.SocialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/login/oauth2", produces = "application/json")
public class LoginController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    @Operation(
            summary = "소셜 로그인",
            description = "소셜 프로바이더를 통해 로그인하고 JWT 토큰을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "JWT 토큰을 반환합니다.")
            }
    )
    @PostMapping("/{provider}")
    public ResponseEntity<JwtResponse> socialLogin(
            @RequestBody String code, @PathVariable SocialType provider) {
        AuthClaims loggedInUser = loginService.socialLogin(code, provider);
        String token = jwtUtil.createToken(loggedInUser, Instant.now());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/{provider}")
    public ResponseEntity<JwtResponse> socialLoginTest(
            @RequestParam String code, @PathVariable SocialType provider) {
        AuthClaims auth = loginService.socialLogin(code, provider);
        String token = jwtUtil.createToken(auth, Instant.now());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Operation(
            summary = "OAuth URL 생성",
            description = "OAuth 인증을 위한 URL을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OAuth 인증 URL을 반환합니다.")
            }
    )
    @GetMapping("/{provider}/authorize")
    public ResponseEntity<UriResponse> getAuthorizationUrl(@PathVariable SocialType provider) {
        String authorizationUrl = loginService.getAuthorizationUrl(provider);
        return ResponseEntity.ok(new UriResponse(authorizationUrl));
    }
}