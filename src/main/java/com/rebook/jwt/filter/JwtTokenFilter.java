//package com.rebook.jwt.filter;
//
//
//import com.rebook.jwt.JwtUtil;
//import com.rebook.jwt.service.JwtService;
//import com.rebook.user.domain.UserEntity;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Setter
//@RequiredArgsConstructor
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader("Authorization");
//
//        if (authorizationHeader != null) {
//            // jwt 토큰이 있는 경우 헤더에서 토큰 추출
//            String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
//            if (!jwtUtil.isTokenExpired(token) && jwtUtil.isTokenNotManipulated(token)) {
//                UserEntity user = jwtService.getLoggedInUser(authorizationHeader);
//                request.setAttribute("user", user);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//}
