package com.howread.user.controller;

import com.howread.jwt.JwtUtil;
import com.howread.jwt.controller.TokenController;
import com.howread.jwt.service.JwtService;
import com.howread.jwt.service.dto.RefreshAndAccessTokenDto;
import com.howread.user.exception.TokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TokenController.class)
class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtUtil jwtUtil;

    @DisplayName("리프레시 토큰이 유효하다면 액세스 토큰을 재발급하여 반환한다.")
    @Test
    void goodRefreshTokenReturnNewAccessToken() throws Exception {
        // given
        String newRefreshToken = "newRefreshToken";
        String newAccessToken = "newAccessToken";

        // when
        when(jwtUtil.extractTokenFromHeader(anyString())).thenReturn(newRefreshToken);
        when(jwtService.renewalRefreshAndAccessToken(newRefreshToken)).thenReturn(new RefreshAndAccessTokenDto(newAccessToken, newRefreshToken));

        // then
        mockMvc.perform(post("/api/v1/token/refresh")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + newRefreshToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accessToken").value(newAccessToken))
                .andExpect(jsonPath("$.refreshToken").value(newRefreshToken));
    }

    @DisplayName("리프레시 토큰이 유효하지 않은 경우 401 상태 코드를 반환한다")
    @Test
    void badRefreshTokenReturnHttpStatusCode401() throws Exception {
        // given
        String invalidRefreshToken = "invalid-refresh-token";

        // when
        when(jwtUtil.extractTokenFromHeader(anyString())).thenReturn(invalidRefreshToken);
        when(jwtService.renewalRefreshAndAccessToken(invalidRefreshToken)).thenThrow(new TokenException("INVALID_TOKEN", "로그인 실패", "다시 로그인 해주세요."));

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/token/refresh")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidRefreshToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}