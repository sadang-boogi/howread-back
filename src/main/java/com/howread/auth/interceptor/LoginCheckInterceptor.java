package com.howread.auth.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.howread.auth.annotation.LoginRequired;
import com.howread.common.exception.ExceptionCode;
import com.howread.jwt.service.JwtService;
import com.howread.user.exception.TokenException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethodAnnotation(LoginRequired.class);
        if (loginRequired == null) {
            // 애노테이션이 없는 경우
            return true;
        }

        // Authorization 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        boolean optional = loginRequired.optional();

        if (isNotExist(authorizationHeader)) {
            if (!optional) {
                // optional이 false -> 토큰이 없는 경우 예외 발생
                throw new TokenException(ExceptionCode.TOKEN_INVALID);
            } else {
                // optional이 true -> 토큰이 없는 경우 통과
                return true;
            }
        }

        String token = jwtService.extractTokenFromHeader(authorizationHeader);

        // 토큰이 유효한 경우 AuthClaims 추출
        request.setAttribute("authClaims", jwtService.getClaims(token));

        return true;
    }

    private boolean isNotExist(final String accessToken) {
        return accessToken == null || accessToken.isBlank();
    }

}
