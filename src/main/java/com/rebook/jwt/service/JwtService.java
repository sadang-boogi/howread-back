package com.rebook.jwt.service;

import com.rebook.common.exception.ExceptionCode;
import com.rebook.common.exception.NotFoundException;
import com.rebook.jwt.JwtUtil;
import com.rebook.user.exception.TokenException;
import com.rebook.user.service.dto.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtUtil jwtUtil;

    public LoggedInUser getLongedInUser(String authorizationHeader) {
        if (!jwtUtil.isIncludeTokenPrefix(authorizationHeader)) {
            throw new TokenException(ExceptionCode.TOKEN_INVALID);
        }
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);

        if (jwtUtil.isTokenExpired(token) || !jwtUtil.isTokenNotManipulated(token)) {
            throw new TokenException(ExceptionCode.TOKEN_INVALID);
        }

        return jwtUtil.extractUserFromToken(token);
    }

}
