package com.rebook.user.interceptor;

import com.rebook.user.exception.TokenException;
import com.rebook.user.service.dto.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.rebook.user.exception.TokenExceptionCode.TOKEN_INVALID;
import static com.rebook.user.exception.TokenExceptionCode.TOKEN_MISSING;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!jwtUtil.isIncludeTokenPrefix(header)) {
            throw new TokenException(TOKEN_MISSING);
        }

        String token = jwtUtil.extractTokenFromHeader(header);
        if (jwtUtil.isTokenExpired(token)) {
            throw new TokenException(TOKEN_INVALID);
        }

        if (jwtUtil.isTokenNotManipulated(token)) {
            throw new TokenException(TOKEN_INVALID);
        }

        return true;
    }
}