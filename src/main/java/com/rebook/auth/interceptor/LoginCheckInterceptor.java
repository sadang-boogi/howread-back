package com.rebook.auth.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.exception.ExceptionCode;
import com.rebook.jwt.service.JwtService;
import com.rebook.user.exception.TokenException;

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

		HandlerMethod handlerMethod = (HandlerMethod)handler;
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

		// 토큰이 유효한 경우 사용자 정보를 요청에 설정
		request.setAttribute("authClaims", jwtService.getClaims(token));

		return true;
	}

	private boolean isNotExist(final String accessToken) {
		return accessToken == null || accessToken.isBlank();
	}

}
