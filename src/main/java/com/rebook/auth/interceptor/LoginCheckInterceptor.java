package com.rebook.auth.interceptor;

import static com.rebook.common.exception.ExceptionCode.*;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.rebook.auth.annotation.LoginOptional;
import com.rebook.auth.annotation.LoginRequired;
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
		LoginOptional loginOptional = handlerMethod.getMethodAnnotation(LoginOptional.class);

		if (loginRequired != null) {
			// 로그인 필수인 경우
			validateAndSetAuthClaims(request, true);
		} else if (loginOptional != null) {
			// 로그인 옵션인 경우
			validateAndSetAuthClaims(request, false);
		}

		return true;
	}

	private void validateAndSetAuthClaims(final HttpServletRequest request, final boolean isLoginRequired) {
		String authorizationHeader = request.getHeader("Authorization");

		// 로그인 필수 + 토큰 필요인 경우 예외 처리
		if (isLoginRequired && isNotExist(authorizationHeader))
			throw new TokenException(TOKEN_INVALID);

		if (!isNotExist(authorizationHeader)) {
			// 토큰이 있는 경우
			String token = jwtService.extractTokenFromHeader(authorizationHeader);
			request.setAttribute("authClaims", jwtService.getClaims(token));
		} else {
			// 토큰이 없는 경우 null로 설정
			request.setAttribute("authClaims", null);
		}
	}

	private boolean isNotExist(final String accessToken) {
		return accessToken == null || accessToken.isBlank();
	}

}
