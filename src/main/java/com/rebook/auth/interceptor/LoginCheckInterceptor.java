package com.rebook.auth.interceptor;

import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.exception.ExceptionCode;
import com.rebook.jwt.JwtUtil;
import com.rebook.jwt.service.JwtService;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.exception.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final JwtUtil jwtUtil;

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

        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            //토큰이 없는 경우
            throw new TokenException("TOKEN_REQUIRED", "로그인 필요", "로그인이 필요한 기능입니다.");
        }

        // 토큰 유효성 검사
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);

        if (!jwtUtil.isTokenNotManipulated(token)) {
            throw new TokenException("INVALID_TOKEN", "로그인 실패", "다시 로그인 해주세요.");
        }

        // 만료되어 로그인이 필요한 경우
        if (jwtUtil.isTokenExpired(token)) {
            throw new TokenException("TOKEN_EXPIRED", "로그인 필요", "세션이 만료되었으므로 다시 로그인 해주세요.");
        }

        // 유효한 토큰인 경우 사용자 정보를 요청에 설정
        UserEntity user = jwtService.getLoggedInUser(token);
        request.setAttribute("user", user);

        return true;
    }
}
