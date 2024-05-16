package com.rebook.jwt.service;

import com.rebook.common.exception.ExceptionCode;
import com.rebook.common.exception.NotFoundException;
import com.rebook.jwt.JwtUtil;
import com.rebook.user.service.dto.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtUtil jwtUtil;

    public Long getUserIdFromToken(String authorizationHeader) {
        if (!jwtUtil.isIncludeTokenPrefix(authorizationHeader)) {
            throw new NotFoundException(ExceptionCode.TOKEN_MISSING);
        }
        String token = jwtUtil.extractTokenFromHeader(authorizationHeader);
        LoggedInUser loggedInUser = jwtUtil.extractUserFromToken(token);
        return loggedInUser.getUserId();
    }

}
