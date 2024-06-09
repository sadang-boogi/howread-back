package com.rebook.jwt.filter;


import com.rebook.jwt.JwtUtil;
import com.rebook.jwt.service.JwtProperties;
import com.rebook.jwt.service.JwtService;
import com.rebook.user.exception.TokenException;
import com.rebook.user.service.dto.LoggedInUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.rebook.common.exception.ExceptionCode.TOKEN_INVALID;

@Setter
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 토큰이 있는 경우 LoggedInUser 생성
        String authorizationHeader = request.getHeader("Authorization");
        try {
            LoggedInUser user = jwtService.getLongedInUser(authorizationHeader);
            request.setAttribute("loggedInUser", user);
        } catch (Exception e) {
            // 토큰이 유효하지 않은 경우에도 api 처리 가능
            request.setAttribute("loggedInUser", null);
        }

        filterChain.doFilter(request, response);

    }
}
