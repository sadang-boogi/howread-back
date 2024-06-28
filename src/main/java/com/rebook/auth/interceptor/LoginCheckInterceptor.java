package com.rebook.auth.interceptor;

import com.rebook.auth.annotation.LoginRequired;
import com.rebook.jwt.service.JwtService;
import com.rebook.user.exception.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);

        if (loginRequired == null) {
            return true;
        }

        // Authorization 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        // 토큰이 없는 경우
        if (isNotExist(authorizationHeader)) {
            throw new TokenException("TOKEN_REQUIRED", "로그인 필요", "로그인이 필요한 기능입니다.");
        }

        String token = jwtService.extractTokenFromHeader(authorizationHeader);

        // 유효한 토큰인 경우 사용자 정보를 요청에 설정
        request.setAttribute("authClaims", jwtService.getClaims(token));

        return true;
    }

    private boolean isNotExist(final String accessToken) {
        return accessToken == null || accessToken.isBlank();
    }
}
