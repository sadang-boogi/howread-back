package com.rebook.user.interceptor;

import com.rebook.user.exception.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.rebook.user.exception.TokenExceptionCode.TOKEN_MISSING;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            throw new TokenException(TOKEN_MISSING);
        }

        jwtInterceptor.preHandle(request, response, handler);

        return true;
    }
}